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

public class Right extends JPanel {
	private List<Agent> agents;
	private Random ran = new Random();
	public Right(){
		super();
		agents = new ArrayList<>();
	}
	@Override 
	public void paintComponent(Graphics g) { // <-- HERE!
		super.paintComponent(g);
        setBackground(Color.white);
        // TODO 
        //We need to check some misunderstanding with the initial position
        for(Agent a:agents){
        	g.setColor(a.getColor());
        	g.drawOval(a.getX()-(int)a.getRange(), a.getY()-(int)a.getRange(), 2*(int)a.getRange(),  2*(int)a.getRange());
        	
        	g.drawOval(a.getX()-1, a.getY()-1,2,2);
        }
        Graphics2D g2d = (Graphics2D) g;
        makeConnection(g2d,agents);
        
    }
	public void startSimulation(int agents,float bandwidth,float sizeFile){
		this.agents = new ArrayList<>();
		revalidate();
		
		for(int i=0; i<agents; i++){
			Color a = new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
			this.agents.add(new Agent(35+ran.nextInt(450), 35+ran.nextInt(450), 
					5+ran.nextInt(30),a));
			
		}
		Timer timer = new Timer(150, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Agent a:Right.this.agents){
					a.run();
					repaint();
				}
				
			}
		});
		timer.setRepeats(true);
		timer.start();
		
	}
	public void makeConnection(Graphics2D g2d,List<Agent>agents){
		g2d.setStroke(new BasicStroke(3.0f));
		g2d.setColor(new Color(0, 255, 0));
		for(int i=0; i<agents.size(); i++){
			for(int j=0; j<agents.size(); j++){
				if(i!=j){
					double distance = (agents.get(i).getX()-agents.get(j).getX())*(agents.get(i).getX()-agents.get(j).getX()) +
							(agents.get(i).getY()-agents.get(j).getY()) *(agents.get(i).getY()-agents.get(j).getY());
					double dis = Math.sqrt(distance);
					if(dis<agents.get(i).getRange() || dis<agents.get(j).getRange()){
						g2d.drawLine(agents.get(i).getX(), agents.get(i).getY(),
								agents.get(j).getX(), agents.get(j).getY());
					}
					
				}
			}
		}
		
	}

}
