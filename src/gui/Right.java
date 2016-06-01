package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
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
	private static final int MAX_COMMUNITY = 4;
	private Random ran = new Random();
	private Timer timer;
	private float minSizeFile;
	public static List<Community> communities;
	Second current;
	public Right(){
		super();
		communities =  new ArrayList<>();
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
        for(Community c:communities){
        	g.setColor(Color.black);
        	g.drawOval(c.getX()-c.getRadio()/2, c.getY()-c.getRadio()/2, c.getRadio(),  c.getRadio());
        }
        for(Agent a:agents){
        	if(a.isOn()){
        		g.setColor(a.getColor());
        		g.drawOval(a.getX()-(int)a.getRange(), a.getY()-(int)a.getRange(), 2*(int)a.getRange(),  2*(int)a.getRange());
        		g.drawOval(a.getX()-1, a.getY()-1,2,2);
        		g.drawString(a.getPercent(), a.getX()-(int)a.getRange(), a.getY());
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
		generateCommunities();
		revalidate();
		Color communityOne = new Color(255, 0, 0);
		Point p = generatePointInside(communities.get(0).getX(), communities.get(0).getY(), 
				communities.get(0).getRadio());
		this.agents.add(new Agent(p.x,p.y, 
				25+ran.nextInt(5),communityOne,"node1->1",sizeFile,sizeFile,bandwidth/(1.0f+ran.nextInt(4)),""+1));
		Color communityTwo = new Color(0, 255, 255);
		Color communityThree = Color.blue;
		Color communityFour = Color.yellow;
		p = generatePointInside(communities.get(3).getX(), communities.get(3).getY(), 
				communities.get(3).getRadio());
		this.agents.add(new Agent(p.x, p.y, 
				25+ran.nextInt(5),communityFour,"node1->4",sizeFile,sizeFile,bandwidth/(1.0f+ran.nextInt(4)),""+4));
		/*p = generatePointInside(communities.get(3).getX(), communities.get(3).getY(), 
				communities.get(3).getRadio());
		this.agents.add(new Agent(p.x, p.y, 
				20+ran.nextInt(5),communityTwo,"node1->2",sizeFile,sizeFile,bandwidth/(1.0f+ran.nextInt(4)),""+2));
		p = generatePointInside(communities.get(3).getX(), communities.get(3).getY(), 
				communities.get(3).getRadio());
		this.agents.add(new Agent(p.x, p.y, 
				20+ran.nextInt(5),communityThree,"node1->3",sizeFile,sizeFile,bandwidth/(1.0f+ran.nextInt(4)),""+3));*/
		int agentsPerCommunity = agents/MAX_COMMUNITY;
		for(int i=0; i<agentsPerCommunity; i++){
			p = generatePointInside(communities.get(0).getX(), communities.get(0).getY(), 
					communities.get(0).getRadio());
			this.agents.add(new Agent(p.x, p.y, 
					25+ran.nextInt(5),communityOne,"node"+(i+2)+"->1",sizeFile,(float)(ran.nextInt((int)sizeFile)),bandwidth/(1.0f+ran.nextInt(4)),""+1));
			p = generatePointInside(communities.get(1).getX(), communities.get(1).getY(), 
					communities.get(1).getRadio());
			this.agents.add(new Agent(p.x, p.y, 
					25+ran.nextInt(5),communityTwo,"node"+(i+2)+"->2",sizeFile,(float)(ran.nextInt((int)sizeFile)),bandwidth/(1.0f+ran.nextInt(4)),""+2));
			p = generatePointInside(communities.get(2).getX(), communities.get(2).getY(), 
					communities.get(2).getRadio());
			this.agents.add(new Agent(p.x, p.y, 
					25+ran.nextInt(5),communityThree,"node"+(i+2)+"->3",sizeFile,(float)(ran.nextInt((int)sizeFile)),bandwidth/(1.0f+ran.nextInt(4)),""+3));
			p = generatePointInside(communities.get(3).getX(), communities.get(3).getY(), 
					communities.get(3).getRadio());
			this.agents.add(new Agent(p.x, p.y, 
					25+ran.nextInt(5),communityFour,"node"+(i+2)+"->4",sizeFile,(float)(ran.nextInt((int)sizeFile)),bandwidth/(1.0f+ran.nextInt(4)),""+4));
			
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
							&& dis<=(agents.get(j).getRange()+agents.get(i).getRange()) && isCircleInside(agents.get(i),agents.get(j),communities)){
						if(!agents.get(i).isCommunicationWithOhterCommunities()){
							if(agents.get(i).getSizeFileTemp()<agents.get(j).getSizeFileTemp() && agents.get(i).getIdCommunity().equals(agents.get(j).getIdCommunity())){
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
						}else{
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
					}
					if(Math.abs(agents.get(j).getRange()-agents.get(i).getRange())<dis
							&& dis<=(agents.get(j).getRange()+agents.get(i).getRange()) && isCircleInside(agents.get(i),agents.get(j),communities)){
						if(!agents.get(j).isCommunicationWithOhterCommunities()){
							if(agents.get(j).getSizeFileTemp()<agents.get(i).getSizeFileTemp() && agents.get(i).getIdCommunity().equals(agents.get(j).getIdCommunity())){
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
						}else{
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
		}
		for(Agent a: this.agents){
			a.calculateSizeFile();
		}
		
	}
	private void generateCommunities(){
		
		for(int i=0; i<MAX_COMMUNITY/2; i++){
			for(int j=0; j<MAX_COMMUNITY/2; j++){
				int x = 130 + 350*i -85*i;
				int y = 130 + 400*j - 100*j;
				int radio = 250;
				communities.add(new Community(x, y, radio));
			}
		}
	}
	private Point generatePointInside(int x, int y, int radio){
		int signX = ran.nextBoolean() ? 1:-1;
		int signY = ran.nextBoolean() ? 1:-1;
		Point p = new Point(x+(ran.nextInt(radio/4)*signX), y+(ran.nextInt(radio/4)*signY));
		//System.out.println(p.toString()+"-"+x+"-"+y+"-"+radio);
		return p;
	}
	public boolean isFinish(){
		for(Agent a:agents){
			if(a.getSizeFileTemp()<a.getSizeFile()){
				return false;
			}
		}
		return true;
	}
	public boolean isCircleInside(Agent a,Agent b, List<Community> c){
		for(Community c1:c){
			double  dist = Math.hypot((a.getX()-c1.getX()), (a.getY()-c1.getY()));
			double  dist1 = Math.hypot((b.getX()-c1.getX()), (b.getY()-c1.getY()));
			if(dist <=c1.getRadio()/2 && dist1<=c1.getRadio()/2){
				return true;
			}
		}
		return false;
		
	}

}
