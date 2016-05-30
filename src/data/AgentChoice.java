package data;

import java.awt.Color;

public class AgentChoice implements Comparable<AgentChoice>{
	
	private double distance, radio, x, y;
	private int numOfConnections;
	private float sizeFile;
	private float sizeFileDifference;
	private float bandwidth;
	
	
	//This  five variables will sum 50
	private  double WDISTANCE;
	private  double WCONNECTION;
	private  double WRADIO;
	private  double WSIZEFILE;
	private  double WBANDWIDTH;
	
	public AgentChoice(Agent me, Agent a, 
			double WDISTANCE,double WCONNECTION,double WRADIO,
			double WSIZEFILE,double WBANDWIDTH) {
		this.WDISTANCE = WDISTANCE;
		this.WCONNECTION = WCONNECTION;
		this.WRADIO = WRADIO;
		this.WSIZEFILE = WSIZEFILE;
		this.WBANDWIDTH = WBANDWIDTH;
		x = a.getX();
		y = a.getY();
		sizeFile = a.getSizeFileTemp();
		bandwidth = a.getBandwidth();
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
		return (radio * WRADIO) + (distance * WDISTANCE) + numOfConnections * WCONNECTION - bandwidth * WBANDWIDTH 
				-sizeFile * WSIZEFILE;
	}
	private double getAnotherWeight(){
		return  sizeFile * WSIZEFILE + numOfConnections * WCONNECTION - distance * WDISTANCE + (radio * WRADIO);
	}
	
	
}
