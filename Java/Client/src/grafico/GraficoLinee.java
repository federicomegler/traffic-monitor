package grafico;

import java.awt.Color;
import java.awt.List;
import java.util.Random;

import javax.swing.JFrame;

import java.awt.BasicStroke; 

import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset; 
import org.jfree.data.xy.XYSeries; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 
import org.jfree.chart.plot.XYPlot; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.xy.XYSeriesCollection; 
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

public class GraficoLinee extends JFrame {

   public GraficoLinee(String applicationTitle, String chartTitle,String x, String y, List lista) {
      super(applicationTitle);
      JFreeChart xylineChart = ChartFactory.createXYLineChart(
         chartTitle ,
         x ,
         y ,
         createDataset(lista) ,
         PlotOrientation.VERTICAL ,
         true , true , false);
         
      ChartPanel chartPanel = new ChartPanel( xylineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      final XYPlot plot = xylineChart.getXYPlot( );
      
      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
      renderer.setSeriesPaint( 0 , Color.RED );
      renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
      plot.setRenderer( renderer ); 
      setContentPane( chartPanel );
      this.pack( );          
      RefineryUtilities.centerFrameOnScreen(this);          
      this.setVisible( true ); 
   }
	
   private XYDataset createDataset( List o) {         
		DefaultXYDataset ds = new DefaultXYDataset();
		double[][] data = new double[2][o.getItemCount()];
		int i=0;
		while(i != o.getItemCount()) {
			String[] s = o.getItem(i).split("\\£");
			data[0][i]=Double.parseDouble(s[1]);  
			data[1][i]=Double.parseDouble(s[0]);
			++i;
		}
		ds.addSeries("auto", data);
		return ds;	
   }

}
