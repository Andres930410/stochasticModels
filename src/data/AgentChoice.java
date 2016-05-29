package data;

import java.awt.Color;

public class AgentChoice implements Comparable<AgentChoice>{
	
	private double distance, radio, x, y;
	private int numOfConnections;
	private float sizeFile;
	private float sizeFileDifference;
	private float bandwidth;
	
	private static final double WDISTANCE = 0.5;
	private static final double WCONNECTION = 25;
	private static final double WRADIO = 1;
	private static final double WSIZEFILE = 10;
	private static final double WBANDWIDTH = 4;
	
	public AgentChoice(Agent me, Agent a) {
		x = a.getX();
		y = a.getY();
		sizeFile = a.getSizeFileTemp();
		bandwidth = a.getTransferVelocity();
		sizeFileDifference = me.getSizeFileTemp()- me.getSizeFile();
		numOfConnections = a.getActiveConnections();
		distance = Math.hypot(me.getX()-x,me.getY() - y);
		radio = a.getRange();
	}
	
	

	public double getDistance() {
		return distance;
	}



	public void setDistance(double distance) {
		this.distance = distance;
	}



	public double getRadio() {
		return radio;
	}



	public void setRadio(double radio) {
		this.radio = radio;
	}



	public double getX() {
		return x;
	}



	public void setX(double x) {
		this.x = x;
	}



	public double getY() {
		return y;
	}



	public void setY(double y) {
		this.y = y;
	}



	@Override
	public int compareTo(AgentChoice o) {
		if(sizeFileDifference == 0){//This node has the 100% of the file
			return Double.compare(getWeight(), o.getAnotherWeight());
		}else{
			return Double.compare(this.getWeight(), o.getWeight());
		}
	}

	private double getWeight(){
		return (radio * WRADIO) + (distance * WDISTANCE) + numOfConnections * WCONNECTION - bandwidth * WBANDWIDTH;
	}
	private double getAnotherWeight(){
		return  sizeFile *WSIZEFILE + numOfConnections *WCONNECTION;
	}
	
	
}
