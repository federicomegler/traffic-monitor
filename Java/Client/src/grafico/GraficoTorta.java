package grafico;
import java.awt.Event;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import graficaUtente.PaginaUtente;

 
public class GraficoTorta extends JFrame {
   private int intenso;
   private int medio;
   private int regolare;
   
   public GraficoTorta(String title, int intenso, int medio, int regolare) {
      super(title); 
      
      this.setIntenso(intenso);
      this.setMedio(medio);
      this.setRegolare(regolare);
      setContentPane(createDemoPanel( ));
      this.setSize( 560 , 367 );    
      RefineryUtilities.centerFrameOnScreen( this ); 
      this.setVisible( true );
   }
   
   public PieDataset createDataset() {
      DefaultPieDataset dataset = new DefaultPieDataset( );
      dataset.setValue( "Intenso" , getIntenso() );  
      dataset.setValue( "Medio" , medio );   
      dataset.setValue( "Regolare" , regolare );
  
      return dataset;         
   }
   
   private JFreeChart createChart( PieDataset dataset ) {
      JFreeChart chart = ChartFactory.createPieChart(      
         "Traffico",   // chart title 
         dataset,          // data    
         true,             // include legend   
         true, 
         false);

      return chart;
   }
   
   public JPanel createDemoPanel( ) {
      JFreeChart chart = createChart(createDataset( ) );  
      return new ChartPanel( chart ); 
   }


public int getIntenso() {
	return intenso;
}

public void setIntenso(int intenso) {
	this.intenso = intenso;
}

public int getMedio() {
	return medio;
}

public void setMedio(int medio) {
	this.medio = medio;
}

public int getRegolare() {
	return regolare;
}

public void setRegolare(int regolare) {
	this.regolare = regolare;
}
}
