package data;

import java.awt.Color;
import java.util.Random;

import javax.swing.SwingUtilities;

import businessLogic.Main;

public class Agent  {
	// TODO
	//This class will manage all the information about the network point
	//This class will modeled as Thread
	private int x;
	private int y;
	private double range;
	private Random rand;
	private Color color;
	
	
	public Agent(int x, int y, double range,Color color) {
		super();
		rand = new Random();
		this.x = x;
		this.color = color;
		this.y = y;
		this.range = range;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public double getRange() {
		return range;
	}
	public void setRange(double range) {
		this.range = range;
	}
	public Color getColor(){
		return color;
	}
	
	public void run() {
		//Here we have to move the agents with some parameters and logic for the begin it will a random movement
		int dxSymbol = rand.nextInt(100);
		int temp = dxSymbol <90 ?1:-1;
		int dx = 1 + rand.nextInt(10);
		int dy = 1 + rand.nextInt(10);
		dx *=temp;
		dy *=temp;
		while(!(dx+x-range>0 && dx+x+range<550 && dy+y-range>0 && dy+y+range<550)){
			dxSymbol = rand.nextInt(100);
			temp = dxSymbol <50 ?1:-1;
			dx = 1 + rand.nextInt(10);
			dy = 1 + rand.nextInt(10);
			dx *=temp;
			dy *=temp;
		}
		x+=dx;
		y+=dy;
		//System.out.println(x+" "+y);
		
	}
	

	
	
}
