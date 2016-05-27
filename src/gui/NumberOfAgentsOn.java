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
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

import businessLogic.Main;
import data.Statistics;

public class NumberOfAgentsOn extends ApplicationFrame {
	public NumberOfAgentsOn(){
		super("Numero de agentes encendidos en el tiempo");
		setContentPane(createPanel());
	}
	public JFreeChart createChart(XYDataset dataset){
		return ChartFactory.createTimeSeriesChart(             
			      "Agentes activos", 
			      "segundos",              
			      "numero",              
			      dataset,             
			      false,              
			      false,              
			      false);
		 
	}
	public XYDataset createDataSet(){
		Statistics s = Main.getInstance().getStatistics();
		
		TimeSeries series  =  new TimeSeries("Agentens on");
		SortedSet<Second> keys = new TreeSet<>(s.getNumOfAgentsOn().keySet());
		for(Second key:keys){
			series.add(key, s.getNumOfAgentsOn().get(key));
		}
		
		return new TimeSeriesCollection(series);
	}
	
	public JPanel createPanel(){
		JFreeChart chart = createChart(createDataSet() );  
	    ChartPanel chartPanel = new ChartPanel( chart );
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
