package gui;

import java.awt.event.WindowEvent;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;

import businessLogic.Main;
import data.Statistics;

public class NumberOfConnections extends ApplicationFrame{
	public NumberOfConnections(){
		super("Numero de conecciones por agente");
		setContentPane(createPanel());
	}
	public JFreeChart createChart(PieDataset dataset){
		JFreeChart chart = ChartFactory.createPieChart(
				"Numero de conecciones", 
				dataset, 
				true, 
				true, 
				false );
		return chart;
	}
	public PieDataset createDataSet(){
		Statistics s = Main.getInstance().getStatistics();
		DefaultPieDataset dataset =  new DefaultPieDataset();
		SortedSet<String> keys = new TreeSet<>(s.getNumOfConnections().keySet());
		for(String key:keys){
			dataset.setValue(key, s.getNumOfConnections().get(key));
		}
		return dataset;
	}
	
	public JPanel createPanel(){
		JFreeChart chart = createChart(createDataSet() );  
		ChartPanel chartPanel =new ChartPanel( chart );
		chartPanel.setPreferredSize( new java.awt.Dimension( 500 , 500 ) );         
	    chartPanel.setMouseZoomable( true , false );
	    return chartPanel;
	}
	@Override
	public void windowClosing(final WindowEvent event) {
		if (event.getWindow() == this) {
			dispose();
		    //System.exit(0);
		}
	}
	

}
