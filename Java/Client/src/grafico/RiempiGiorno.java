package grafico;

import java.rmi.Naming;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.awt.List;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Set;

import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import shared.IntStatistiche;

public class RiempiGiorno {

	public static void riempiGrafico(String nomevia, String inizio, String fine) throws Exception {
		// TODO Auto-generated method stub
		
		int idcentralina;
		int rip_i = 0, rip_m = 0, rip_r = 0, n_auto = 0, vel_med = 0, datap = 0, ora = 0;
		String[] vectday, vecttime, daytime;
		boolean ini = true;
		
		IntStatistiche server = (IntStatistiche) Naming.lookup("rmi://localhost:12345/STATISTICHE");
		List res = server.ottieniDatiStatistiche(inizio,fine);
		List lista = new List();
		
		for(int i=0;i<res.getItemCount();++i)
		{
			
			String s = res.getItem(i);
			String[] v = s.split("\\,");
			daytime = v[2].split("\\ ");
			
			vectday=daytime[0].split("\\/");
			vecttime=daytime[1].split("\\:");
			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			String formattedDateTime = currentDateTime.format(formatter);
			String [] daytimenow=formattedDateTime.split("\\ ");
			String [] currentdata=daytimenow[0].split("\\/");
			if(currentdata[2].equals(vectday[2])) {
				if (ini) { 
					datap=Integer.parseInt(vectday[2]);
					ora=Integer.parseInt(vecttime[0]);
				}
				ini=false;

				if(ora!=Integer.parseInt(vecttime[0])) {
					
					if(n_auto==0)  
						lista.add(0 + "£" + ora);
					else 
						lista.add(vel_med/n_auto + "£" + ora);
					
					//chiamo funzione di sara che mi aggiunge i dati rispettivi all'ora
					vel_med=0;n_auto=0;
					ora=Integer.parseInt(vecttime[0]);
				}


				if(v[3].equals("I"))++rip_i;
				if(v[3].equals("M"))++rip_m;
				if(v[3].equals("R"))++rip_r;
				n_auto += Integer.parseInt(v[1]);
				vel_med += Integer.parseInt(v[0])*Integer.parseInt(v[1]);


			}
		}
		
		if(n_auto==0)  
			lista.add(0 + "£" + ora);
		else 
			lista.add(vel_med/n_auto + "£" + ora);

		RiempiSettimana.riempiSettimana(res, "giorno");
		GraficoTorta torta = new GraficoTorta(nomevia, rip_i, rip_m, rip_r);
		torta.setDefaultCloseOperation(ApplicationFrame.DISPOSE_ON_CLOSE);
		torta.setVisible(true);
		GraficoLinee linee = new GraficoLinee("Grafico", nomevia,"Ore","Velocità",lista);
		linee.setDefaultCloseOperation(ApplicationFrame.DISPOSE_ON_CLOSE);
		linee.setVisible(true);		
	}    
	
	
}
