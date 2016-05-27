package businessLogic;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import data.Statistics;
import gui.FrameFactory;
import gui.Window;

public class Main {
	private static Main instance = null;
	private Statistics statistics;
	private ApplicationFrame statisticsGUI;
	public void startSimulation(int agents,float bandwidth,float sizeFile){
		window.StartSimulation(agents, bandwidth, sizeFile);
	}
	
	public static Main getInstance(){
		if(instance==null){
			synchronized (Main.class) {
				if(instance==null){
					instance =  new Main();
				}
			}
		}
		return instance;
	}
	private Main(){
		window =  new Window();
		statistics = new Statistics();
	}
	public Statistics getStatistics(){
		return statistics;
	}
	public void setStatistics(Statistics statistics){
		this.statistics = statistics;
	}
    private Window window;
    public Window getWindow(){
    	return window;
    }
	public static void main(String...args){
		Main main = getInstance();
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				//window =  new Window();
				main.getWindow().show();
			}
		});
	}

	public void finish() {
		window.finish();
		
	}

	public void showStatistics(String type) {
		FrameFactory f = new FrameFactory();
		statisticsGUI = f.getStatistics(type);
		statisticsGUI.pack( );        
	    RefineryUtilities.centerFrameOnScreen( statisticsGUI );        
	    statisticsGUI.setVisible( true ); 
		
	}

}
