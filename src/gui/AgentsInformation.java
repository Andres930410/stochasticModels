package gui;

import java.awt.event.WindowEvent;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import businessLogic.Main;
import data.Data;
import data.Statistics;

public class AgentsInformation extends ApplicationFrame {
	
	public AgentsInformation(){
		super("Informacion de los agentes");
		setContentPane(createPanel());
	}
	public JPanel createPanel(){
		ChartPanel chartPanel = new ChartPanel( createChar(createDataset()) );
		chartPanel.setPreferredSize(new java.awt.Dimension( 500 , 500 ) );        
	    return chartPanel;
	}
	public JFreeChart createChar(CategoryDataset dataset){
		return ChartFactory.createBarChart("Informacion de los agentes", 
				"Categoria", 
				"Informacion", 
				dataset);
	}
	public CategoryDataset createDataset(){
		Statistics s  = Main.getInstance().getStatistics();
		String seconds = "Segundos para finalizar";
		String inactivity ="Segundos inactivos";
		String numConnectios ="Numero de connexiones";
		
		SortedSet<String> keys = new TreeSet<>(
				s.getAgentsInformation().keySet());
		DefaultCategoryDataset dataset = 
			      new DefaultCategoryDataset( );  
		
		for(String key:keys){
			Data a = s.getAgentsInformation().get(key);
			dataset.addValue( a.getSecondsToFinish() , a.getName() , seconds );   
			dataset.addValue( a.getSecondsOff() , a.getName() , inactivity );
			dataset.addValue( a.getNumbOfConection() , a.getName() , numConnectios );   
			
		}
		return dataset;
	}
	@Override
	public void windowClosing(final WindowEvent event) {
		if (event.getWindow() == this) {
			dispose();
		    //System.exit(0);
		}
	}

}
