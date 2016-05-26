package businessLogic;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import gui.Window;

public class Main {
private static Main instance = null;
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

}
