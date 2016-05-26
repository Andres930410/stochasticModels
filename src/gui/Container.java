package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Container extends JPanel {
	private Right right;
	private Left left;
	
	public Container(){
		super(new GridBagLayout());
		right = new Right();
		GridBagConstraints rightConstraints = new GridBagConstraints();
		rightConstraints.fill = GridBagConstraints.HORIZONTAL;
		rightConstraints.weightx = 0.7;
		rightConstraints.gridx = 1;
		rightConstraints.gridy = 0;
		rightConstraints.ipady = 600; 
		//rightConstraints.fill = GridBagConstraints.VERTICAL;
		left =  new Left();
		JScrollPane scroll =  new JScrollPane(left);
		GridBagConstraints leftConstrains = new GridBagConstraints();
		leftConstrains.fill = GridBagConstraints.HORIZONTAL;
		leftConstrains.weightx = 0.3;
		leftConstrains.gridx = 0;
		leftConstrains.gridy = 0;
		leftConstrains.ipady = 600; 
		add(scroll,leftConstrains);
		add(right,rightConstraints);
		
	}
	public void startSimulation(int agents,float bandwidth,float sizeFile){
		right.startSimulation(agents, bandwidth, sizeFile);
		repaint();
	}
}
