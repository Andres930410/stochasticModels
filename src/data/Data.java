package data;

public class Data {
	private int secondsToFinish;
	private int secondsOff;
	private int numbOfConection;
	private String name;
	
	
	public Data(int secondsToFinish, int secondsOff, int numbOfConection,String name) {
		super();
		this.secondsToFinish = secondsToFinish;
		this.secondsOff = secondsOff;
		this.numbOfConection = numbOfConection;
		this.name = name;
	}
	public int getSecondsToFinish() {
		return secondsToFinish;
	}
	public void setSecondsToFinish(int secondsToFinish) {
		this.secondsToFinish = secondsToFinish;
	}
	public int getSecondsOff() {
		return secondsOff;
	}
	public void setSecondsOff(int secondsOff) {
		this.secondsOff = secondsOff;
	}
	public int getNumbOfConection() {
		return numbOfConection;
	}
	public void setNumbOfConection(int numbOfConection) {
		this.numbOfConection = numbOfConection;
	}
	public String getName(){
		return name;
	}
	
}
