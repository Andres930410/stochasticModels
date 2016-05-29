package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import data.*;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.data.time.Second;

import businessLogic.Main;

public class Right extends JPanel {
	private List<Agent> agents;
	private Random ran = new Random();
	private Timer timer;
	private float minSizeFile;
	Second current;
	public Right(){
		super();
		minSizeFile = Float.MAX_VALUE;
		current = new Second();
		timer = new Timer(100, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Collections.sort(Right.this.agents, new Comparator<Agent>() {
					public int  compare(Agent o1,
					          Agent o2){
						if(o1.getSizeFileTemp()<o2.getSizeFileTemp()){
							return -1;
						}else if(o1.getSizeFileTemp()>o2.getSizeFileTemp()){
							return 1;
						}
						return 0;
								
					}
				});
				minSizeFile =  agents.get(0).getSizeFileTemp();
				for(Agent a:Right.this.agents){
					a.run(Right.this.agents);
				}
				int count = 0;
				for(Agent a:Right.this.agents){
					if(a.isOn()){
						count++;
					}
				}
				current = ( Second ) current.next( ); 
				Main.getInstance().getStatistics().
				addInformationAboutNumOfAgentsOn(current, count);
				repaint();
				
			}
		});
		timer.setRepeats(true);
		agents = new ArrayList<>();
	}
	@Override 
	public void paintComponent(Graphics g) { // <-- HERE!
		super.paintComponent(g);
        setBackground(Color.white);
        
        
        // TODO 
        //We need to check some misunderstanding with the initial position
        Graphics2D g2d = (Graphics2D) g;
        makeConnection(g2d,agents);
        for(Agent a:agents){
        	if(a.isOn()){
        		g.setColor(a.getColor());
        		g.drawOval(a.getX()-(int)a.getRange(), a.getY()-(int)a.getRange(), 2*(int)a.getRange(),  2*(int)a.getRange());
        		g.drawOval(a.getX()-1, a.getY()-1,2,2);
        		g.drawString(a.getProcent(), a.getX()-(int)a.getRange(), a.getY());
        	}
        }
        minSizeFile = Float.MAX_VALUE;
        if(isFinish() && !this.agents.isEmpty()){
        	for(Agent a:this.agents){
        		Data data = new Data(a.getSecondToFinish(), a.getSecondOff(),
        				a.numOfConnections(),a.getName());
        		Main.getInstance().getStatistics().addAgentsInformation(a.getName(), 
        				data);
            	Main.getInstance().getStatistics().addInformationAboutNumOfConnections(a.getName(), a.numOfConnections());
        	}
        	timer.stop();
        	Main.getInstance().finish();
        }
        
        
    }
	public void startSimulation(int agents,float bandwidth,float sizeFile){
		this.agents = new ArrayList<>();
		revalidate();
		Color a = new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
		this.agents.add(new Agent(50+ran.nextInt(430), 50+ran.nextInt(430), 
				25+ran.nextInt(30),a,"node0",sizeFile,sizeFile,bandwidth/(1.0f+ran.nextInt(4))));
		a = new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
		this.agents.add(new Agent(50+ran.nextInt(430), 50+ran.nextInt(430), 
				25+ran.nextInt(30),a,"node1",sizeFile,sizeFile,bandwidth/(1.0f+ran.nextInt(4))));
		for(int i=2; i<agents; i++){
			a = new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
			this.agents.add(new Agent(50+ran.nextInt(430), 50+ran.nextInt(430), 
					25+ran.nextInt(30),a,"node"+i,sizeFile,(float)(ran.nextInt((int)sizeFile)),bandwidth/(1.0f+ran.nextInt(4))));
			
		}
		
		
		timer.start();
		
	}
	public void makeConnection(Graphics2D g2d,List<Agent>agents){
		//System.out.println(minSizeFile);
		g2d.setStroke(new BasicStroke(3.0f));
		g2d.setColor(new Color(0, 255, 0));
		for(int i=0; i<agents.size(); i++){
			agents.get(i).setActiveConnections(1);
			agents.get(i).setMainObjective(false);
		}
		for(int i=0; i<agents.size(); i++){
			for(int j=0; j<agents.size(); j++){
				if(i!=j){
					
					double distance = (agents.get(i).getX()-agents.get(j).getX())*(agents.get(i).getX()-agents.get(j).getX()) +
							(agents.get(i).getY()-agents.get(j).getY()) *(agents.get(i).getY()-agents.get(j).getY());
					double dis = Math.sqrt(distance);
					if(Math.abs(agents.get(j).getRange()-agents.get(i).getRange())<dis
							&& dis<=(agents.get(j).getRange()+agents.get(i).getRange())){
						if(agents.get(i).getSizeFileTemp()<agents.get(j).getSizeFileTemp()){
							if(agents.get(i).isOn() && agents.get(j).isOn() && agents.get(i).getSizeFileTemp()<agents.get(i).getSizeFile()){
								if(agents.get(j).getSizeFileTemp() == agents.get(j).getSizeFile() &&
										agents.get(i).getSizeFileTemp()==minSizeFile || (agents.get(j).getSizeFileTemp() == agents.get(j).getSizeFile())){
									agents.get(j).setMainObjective(true);
								}
								//System.out.println(agents.get(i).getName() + " "+ agents.get(i).getSizeFileTemp());
								agents.get(i).setActiveConnections(agents.get(i).getActiveConnections()+1);
								agents.get(i).addConection(agents.get(j).getName());
								agents.get(i).setConnectionSizeFile(agents.get(j).getSizeFileTemp());
								g2d.drawLine(agents.get(i).getX(), agents.get(i).getY(),
										agents.get(j).getX(), agents.get(j).getY());
							}
						}
					}
					if(Math.abs(agents.get(j).getRange()-agents.get(i).getRange())<dis
							&& dis<=(agents.get(j).getRange()+agents.get(i).getRange())){
						if(agents.get(j).getSizeFileTemp()<agents.get(i).getSizeFileTemp()){
							if(agents.get(i).isOn() && agents.get(j).isOn() && agents.get(i).getSizeFileTemp()<agents.get(i).getSizeFile()){
								//System.out.println(agents.get(i).getName() + " "+ agents.get(i).getSizeFileTemp());
								if((agents.get(i).getSizeFileTemp() == agents.get(i).getSizeFile() &&
										agents.get(j).getSizeFileTemp()==minSizeFile) || (agents.get(i).getSizeFileTemp() == agents.get(i).getSizeFile())){
									agents.get(i).setMainObjective(true);
								}
								agents.get(j).setActiveConnections(agents.get(j).getActiveConnections()+1);
								agents.get(j).addConection(agents.get(i).getName());
								agents.get(j).setConnectionSizeFile(agents.get(i).getSizeFileTemp());
								g2d.drawLine(agents.get(i).getX(), agents.get(i).getY(),
										agents.get(j).getX(), agents.get(j).getY());
							}
					
						}
					}
				}
			}
		}
		for(Agent a: this.agents){
			a.calculateSizeFile();
		}
		
	}
	public boolean isFinish(){
		for(Agent a:agents){
			if(a.getSizeFileTemp()<a.getSizeFile()){
				return false;
			}
		}
		return true;
	}

}
