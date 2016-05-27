package data;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.SwingUtilities;

import businessLogic.Main;

public class Agent  {
	// TODO
	//This class will manage all the information about the network point
	//This class will modeled as Thread
	private String name;
	private int seconds;
	private int secondsToFinish;
	private int secondsOff;
	private int x;
	private int y;
	private double range;
	private Random rand;
	private Color color;
	private float sizeFile;
	private float transferVelocity;
	private float sizeFileTemp;
	private float intialSizeFile;
	private Map<String,Float> connections;
	private boolean isOn;
	private int activeConnections;
	
	
	public Agent(int x, int y, double range,Color color,String name,
			float sizeFile,float initialSizeFile,float transferVelocity) {
		super();
		seconds = 0;
		secondsToFinish = 0;
		secondsOff = 0;
		activeConnections = 1;
		isOn =  true;
		connections = new HashMap<>();
		sizeFileTemp = initialSizeFile;
		this.intialSizeFile = initialSizeFile;
		this.name =  name;
		rand = new Random();
		this.x = x;
		this.color = color;
		this.y = y;
		this.range = range;
		this.sizeFile = sizeFile;
		this.transferVelocity =  transferVelocity;
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
	public String getName(){
		return name;
	}
	public String getProcent(){
		return ""+sizeFileTemp/sizeFile *100+"%";
	}
	public float getTransferVelocity(){
		return transferVelocity;
	}
	
	public void run() {
		seconds++;
		//Here we have to move the agents with some parameters and logic for the begin it will a random movement
		int dxSymbol = rand.nextInt(100);
		int temp = dxSymbol <90 ?1:-1;
		int dx = 1 + rand.nextInt(10);
		int dy = 1 + rand.nextInt(10);
		dx *=temp;
		dy *=temp;
		while(!(dx+x-range>5 && dx+x+range<540 && dy+y-range>5 && dy+y+range<540)){
			dxSymbol = rand.nextInt(100);
			temp = dxSymbol <50 ?1:-1;
			dx = 1 + rand.nextInt(10);
			dy = 1 + rand.nextInt(10);
			dx *=temp;
			dy *=temp;
		}
		x+=dx;
		y+=dy;
		int r = (int)(rand.nextGaussian()*10);
		if(r==5){
			this.isOn= true;
		}else if(r==-5){
			secondsOff++;
			this.isOn = false;
		}
		//System.out.println(x+" "+y);
		
	}
	public float getSizeFile(){
		return sizeFile;
	}
	public float getSizeFileTemp(){
		return sizeFileTemp;
	}
	public void addConection(String key){
		if(connections.containsKey(key)){
			connections.put(key, connections.get(key)+transferVelocity/activeConnections);
		}else{
			connections.put(key, transferVelocity/activeConnections);
		}
	}
	public boolean isOn(){
		return isOn;
	}
	public void setOn(boolean isOn){
		this.isOn = isOn;
	}
	public void calculateSizeFile(){
		sizeFileTemp =  intialSizeFile;
		for(Float value: connections.values()){
			sizeFileTemp+=value;
			
		}
		if(sizeFileTemp>sizeFile){
			
			sizeFileTemp = sizeFile;
		}
		if(secondsToFinish ==0 && sizeFile==sizeFileTemp){
			secondsToFinish = seconds;
		}
	}
	public int getSecondOff(){
		return secondsOff;
	}
	public int getSecondToFinish(){
		return secondsToFinish;
	}
	public int getActiveConnections(){
		return activeConnections;
	}
	public void setActiveConnectioins(int activeConnections){
		this.activeConnections = activeConnections;
	}
	public int numOfConnections(){
		return connections.size();
	}
	
	
}
