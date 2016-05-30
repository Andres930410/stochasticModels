package data;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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
	private float connectionSizeFile;
	private boolean mainObjective; 
	private double wradio = 10;
	private double wdistance = 10;
	private double wbandwidth = 10;
	private double wconnection = 10;
	private double wsizefile = 10;
	
	private static final int STEP = 5;
	
	
	public Agent(int x, int y, double range,Color color,String name,
			float sizeFile,float initialSizeFile,float transferVelocity) {
		super();
		seconds = 0;
		mainObjective = false;
		connectionSizeFile = 0;
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
	public float getBandwidth(){
		return transferVelocity/activeConnections;
	}
	
	public void run(List<Agent> agents) {
		if(isOn){
			seconds++;
			
			List<AgentChoice> choices = new ArrayList<>();
			
			generateWieghts(agents);
			System.out.println("radio-bandwidth-connection-distance-wsizefile");
			System.out.println(wradio+" "+ wbandwidth +" "+ wconnection+ " "+
			wdistance+ " " +wsizefile);
			for(Agent agent: agents){
				if(agent.isOn() && sizeFileTemp < agent.getSizeFileTemp() && !name.equals(agent.name)){
					choices.add(new AgentChoice(this, agent,wdistance,wconnection,
							wradio,wsizefile,wbandwidth));
				}else if(agent.isOn() && sizeFileTemp==sizeFile && !name.equals(agent.getName())){//This node has the 100% of the file
					if(agent.sizeFileTemp != sizeFile){
						choices.add(new AgentChoice(this, agent,wdistance,wconnection,
								wradio,wsizefile,wbandwidth));
					}
				}
			}
			if(choices.isEmpty()) return;
			Collections.sort(choices);
			
			//Move to the best choice
			AgentChoice best = choices.get(0);
			double theta = Math.atan2((best.getY() - this.y * 1.0) ,(best.getX()  - x) );
			
			int dx = (int)Math.round(STEP * Math.cos(theta));
			int dy = (int)Math.round(STEP * Math.sin(theta));
			
			if(activeConnections==1 && !mainObjective){
				x += dx;
				y += dy;
			}
			
			if(x<0){
				x=490;
			}
			if(x>500){
				x=10;
			}
			if(y<0){
				y=490;
			}
			if(y>500){
				y=10;
			}
		
		}
		
		turnOff();
		
	}
	public void turnOff(){
		int r = (int)(rand.nextGaussian()*10);
		if(sizeFile==sizeFileTemp && !mainObjective){//These nodes are proner to turn off because they have already complete the file
			
			if((r>=-6 &&r<=-5) || (r>=5 && r<=6)){
				secondsOff++;
				this.isOn= false;
			}else if(r==7 || r==-7){
				this.isOn= true;
			}
		}else{
			if(r==5){
				this.isOn= true;
				
			}else if(r==-5){
				secondsOff++;
				this.isOn = false;
			}
		}
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
		if(sizeFileTemp > connectionSizeFile && connectionSizeFile!=0){
			sizeFileTemp = connectionSizeFile;
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
	public void setActiveConnections(int activeConnections){
		this.activeConnections = activeConnections;
	}
	public int numOfConnections(){
		return connections.size();
	}
	public void setConnectionSizeFile(float connectionSizeFile){
		if(connectionSizeFile> this.connectionSizeFile){
			this.connectionSizeFile = connectionSizeFile;
		}
	}
	public void setMainObjective(boolean mainObjective){
		this.mainObjective = mainObjective;
	}
	public double distance(int x,int y,int x1,int y1){
		return Math.sqrt(Math.pow(x-x1, 2)+Math.pow(y-y1, 2));
	}
	public void generateWieghts(List<Agent> agents){
		int distance = 30 + rand.nextInt(20);
		int radio = 30 + rand.nextInt(30);
		int countDistance = 0;
		int countRadio = 0;
		int countSizeFile = 0;
		int countConnections = 0;
		boolean isAFullNode = false;
		for(Agent a:agents){
			if(sizeFile!=sizeFileTemp && !name.equals(a.getName())){ //This node doesn't have the whole the file
				if(distance(x, y, a.getX(), a.getY())<distance){
					countDistance++;
				}
				if(a.getRange()>=radio){
					countRadio++;
				}
				if(a.getSizeFileTemp()==sizeFile){
					countSizeFile++;
				}
				if(a.getActiveConnections()<2){
					countConnections++;
				}
			}else if (!name.equals(a.getName())){
				isAFullNode = true;
				if(distance(x, y, a.getX(),a.getY())>distance){
					countDistance++;
				}
				if(a.getRange()<=radio){
					countRadio++;
				}
				if(a.getActiveConnections()<2){
					countConnections++;
				}
			}	
		}
		if(isAFullNode){//This is a node with 100% of the file
			wsizefile = 20;
			int total = countDistance + countRadio + countConnections;
			wdistance = 30.0*((double)countDistance/total);
			wradio = 30.0 * ((double)countRadio/total);
			wconnection = 30.0*((double)countConnections/total);
		}else{
			int total = countDistance + countRadio + countConnections +countSizeFile;
			wdistance = 40.0*((double)countDistance/total);
			wradio = 40.0 * ((double)countRadio/total);
			wconnection = 40.0*((double)countConnections/total);
			wsizefile = 40.0*((double)countSizeFile/total);
			wbandwidth = 50.0 - (wdistance + wradio +wconnection +wsizefile);
		}
	}
}
