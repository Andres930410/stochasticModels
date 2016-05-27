package gui;

import org.jfree.ui.ApplicationFrame;

public class FrameFactory {
	public ApplicationFrame getStatistics(String type){
		switch (type) {
		case "conecciones":
			return new NumberOfConnections();
		case "agentes prendidos":
			return new NumberOfAgentsOn();
		case "informacion":
			return new AgentsInformation();

		default:
			return null;
		}
	}
}
