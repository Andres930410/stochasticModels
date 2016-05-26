package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import data.*;

import javax.swing.JPanel;

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
        for(Agent a:agents){
        	g.setColor(new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256)));
        	g.drawOval(a.getX()-(int)a.getRange(), a.getY()-(int)a.getRange(), 2*(int)a.getRange(),  2*(int)a.getRange());
        	
        	g.drawOval(a.getX()-1, a.getY()-1,2,2);
        }
        
    }
	public void startSimulation(int agents,float bandwidth,float sizeFile){
		this.agents = new ArrayList<>();
		revalidate();
		
		for(int i=0; i<agents; i++){
			this.agents.add(new Agent(35+ran.nextInt(450), 35+ran.nextInt(450), 
					5+ran.nextInt(30)));
		}
		
	}

}
