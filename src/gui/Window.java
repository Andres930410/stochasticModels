package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.Timer;

import data.Agent;

public class Window extends JFrame{
	private Container container;
	private Menu menu;
	public Window(){
		super("Stochastic Project");
		container = new Container();
		menu = new Menu();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setJMenuBar(menu);
		setLayout(new BorderLayout());
		add(container,BorderLayout.CENTER);
		
		setVisible(true);
		setResizable(false);
	}
	public void StartSimulation(int agents,float bandwidth,float sizeFile){
		container.startSimulation(agents, bandwidth, sizeFile);
		
	}
	public void finish() {
		container.finish();
		
	}
	

}
