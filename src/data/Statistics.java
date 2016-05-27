package data;

import java.util.HashMap;
import java.util.Map;

import org.jfree.data.time.Second;

public class Statistics {
	//This map has the statistics about the number of connections that
	//Every agent needs to download the file in the program;
	private Map<String, Integer> numOfConnections;
	
	//This map has the statistics about the number of agents who are available
	//at the time
	private Map<Second,Integer> numOfAgentsOn;
	
	//This map has helpful information about the execution of the agent in the
	//program
	private Map<String ,Data> agentsInformation;
	
	public Statistics(){
		numOfConnections =  new HashMap<>();
		numOfAgentsOn = new HashMap<>();
		agentsInformation = new HashMap<>();
	}
	
	public void addInformationAboutNumOfConnections(String key,int value){
		numOfConnections.put(key, value);
	}
	public void addInformationAboutNumOfAgentsOn(Second key,int value){
		numOfAgentsOn.put(key, value);
	}
	public void addAgentsInformation(String key, Data value){
		agentsInformation.put(key, value);
	}

	public Map<String, Integer> getNumOfConnections() {
		return numOfConnections;
	}

	public void setNumOfConnections(Map<String, Integer> numOfConnections) {
		this.numOfConnections = numOfConnections;
	}

	public Map<Second, Integer> getNumOfAgentsOn() {
		return numOfAgentsOn;
	}

	public void setNumOfAgentsOn(Map<Second, Integer> numOfAgentsOn) {
		this.numOfAgentsOn = numOfAgentsOn;
	}

	public Map<String, Data> getAgentsInformation() {
		return agentsInformation;
	}

	public void setAgentsInformation(Map<String, Data> agentsInformation) {
		this.agentsInformation = agentsInformation;
	}
	
	
	
	

}
