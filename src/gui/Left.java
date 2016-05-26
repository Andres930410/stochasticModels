package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import businessLogic.Main;

public class Left extends JPanel {
	private JLabel inicialTextLabel;
	private JLabel numThreadsLabel;
	private JTextField numThreads;
	private JPanel panelThreads;
	
	private JLabel bandwidthLabel;
	private JTextField bandwidth;
	private JPanel panelBandwidth;
	
	private JLabel sizeFileLabel;
	private JTextField sizeFile;
	private JPanel panelSizeFile;
	
	private JButton startSimulation;
	
	public Left(){
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		init();
		
	
		
	
	}
	public void init(){
		inicialTextLabel =  new JLabel("Simulador  red Ad-hoc");
		inicialTextLabel.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		this.setAlignmentX(LEFT_ALIGNMENT);
		add (Box.createRigidArea(new Dimension(0, 15)));
		add (inicialTextLabel);
		add (Box.createRigidArea(new Dimension(0, 35)));
		
		numThreadsLabel =  new JLabel("Numero de agentes");
		numThreads =  new JTextField();
		numThreads.setMaximumSize(new Dimension(400, 30));
		panelThreads = new JPanel();
		panelThreads.setLayout(new BoxLayout(panelThreads, BoxLayout.Y_AXIS));
		panelThreads.setAlignmentX(LEFT_ALIGNMENT);
		panelThreads.add(numThreadsLabel);
		panelThreads.add(numThreads);
		add (panelThreads);
		add (Box.createRigidArea(new Dimension(0, 20)));
		
		bandwidthLabel =  new JLabel("Ancho de banda en megas");
		bandwidth =  new JTextField();
		bandwidth.setMaximumSize(new Dimension(400, 30));
		panelBandwidth = new JPanel();
		panelBandwidth.setLayout(new BoxLayout(panelBandwidth, BoxLayout.Y_AXIS));
		panelBandwidth.setAlignmentX(LEFT_ALIGNMENT);
		panelBandwidth.add(bandwidthLabel);
		panelBandwidth.add(bandwidth);
		add (panelBandwidth);
		add (Box.createRigidArea(new Dimension(0, 20)));
		
		sizeFileLabel =  new JLabel("Tamaño del archivo megas");
		sizeFile =  new JTextField();
		sizeFile.setMaximumSize(new Dimension(400, 30));
		panelSizeFile = new JPanel();
		panelSizeFile.setLayout(new BoxLayout(panelSizeFile, BoxLayout.Y_AXIS));
		panelSizeFile.setAlignmentX(LEFT_ALIGNMENT);
		panelSizeFile.add(sizeFileLabel);
		panelSizeFile.add(sizeFile);
		add (panelSizeFile);
		add (Box.createRigidArea(new Dimension(0, 250)));
		
		startSimulation =  new JButton("Iniciar Simulacion");
		startSimulation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int agents =  Integer.parseInt(numThreads.getText());
					float bandwidthValue = Float.parseFloat(bandwidth.getText());
					float sizeFileValue =  Float.parseFloat(sizeFile.getText());
					Main.getInstance().startSimulation(agents, bandwidthValue, sizeFileValue);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Datos no validos");
				}
			}
		});
		
		add(startSimulation);
	}
}

