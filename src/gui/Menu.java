package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Menu extends JMenuBar{
	private JMenu menuAbout;
	private JMenuItem developers;
	private JMenuItem aboutTheProgram;
	
	public Menu(){
		menuAbout = new JMenu("Acerca de");
		developers =  new JMenuItem("Desarrolladores");
		developers.setMnemonic(KeyEvent.VK_S);
		developers.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Jeferson Cano\n"
						+ "Fabian Conejo\n"
						+ "Andres Gutierrez\n"
						+ "Fernando Muñoz\n"
						+ "Cesar Silva");
				
			}
		});
		aboutTheProgram =  new JMenuItem("Acerca del programa");
		aboutTheProgram.setMnemonic(KeyEvent.VK_A);
		aboutTheProgram.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Simulador de una red adhoc que comparte archivos,\n"
						+ "con el mismo principio de BitTorrent");
				
			}
		});
		menuAbout.add(developers);
		menuAbout.add(aboutTheProgram);
		add(menuAbout);
	}
	
	

}
