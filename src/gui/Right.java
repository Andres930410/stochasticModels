package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
	Second current;
	public Right(){
		super();
		current = new Second();
		timer = new Timer(250, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Agent a:Right.this.agents){
					a.run();
					
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
				20+ran.nextInt(30),a,"name0",sizeFile,sizeFile,bandwidth));
		for(int i=1; i<agents; i++){
			a = new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
			this.agents.add(new Agent(50+ran.nextInt(430), 50+ran.nextInt(430), 
					20+ran.nextInt(30),a,"name"+i,sizeFile,(float)(ran.nextInt((int)sizeFile)),bandwidth));
			
		}
		
		
		timer.start();
		
	}
	public void makeConnection(Graphics2D g2d,List<Agent>agents){
		g2d.setStroke(new BasicStroke(3.0f));
		g2d.setColor(new Color(0, 255, 0));
		for(int i=0; i<agents.size(); i++){
			agents.get(i).setActiveConnectioins(1);
		}
		for(int i=0; i<agents.size(); i++){
			for(int j=0; j<agents.size(); j++){
				if(i!=j){
					double distance = (agents.get(i).getX()-agents.get(j).getX())*(agents.get(i).getX()-agents.get(j).getX()) +
							(agents.get(i).getY()-agents.get(j).getY()) *(agents.get(i).getY()-agents.get(j).getY());
					double dis = Math.sqrt(distance);
					if(dis<agents.get(j).getRange()){
						if(agents.get(i).getSizeFileTemp()<agents.get(j).getSizeFileTemp()){
							if(agents.get(i).isOn() && agents.get(j).isOn() && agents.get(i).getSizeFileTemp()<agents.get(i).getSizeFile()){
								agents.get(i).setActiveConnectioins(agents.get(i).getActiveConnections()+1);
								agents.get(i).addConection(agents.get(j).getName());
								g2d.drawLine(agents.get(i).getX(), agents.get(i).getY(),
										agents.get(j).getX(), agents.get(j).getY());
							}
						}
					}
					if(dis<agents.get(i).getRange()){
						if(agents.get(j).getSizeFileTemp()<agents.get(i).getSizeFileTemp()){
							if(agents.get(i).isOn() && agents.get(j).isOn() && agents.get(i).getSizeFileTemp()<agents.get(i).getSizeFile()){
								agents.get(j).setActiveConnectioins(agents.get(j).getActiveConnections()+1);
								agents.get(j).addConection(agents.get(i).getName());
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
