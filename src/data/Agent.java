package data;

public class Agent {
	// TODO
	//This class will manage all the information about the network point
	//This class will modeled as Thread
	private int x;
	private int y;
	private double range;
	
	
	public Agent(int x, int y, double range) {
		super();
		this.x = x;
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
	
	
}
