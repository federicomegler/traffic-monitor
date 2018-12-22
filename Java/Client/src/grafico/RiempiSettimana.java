package grafico;

import java.awt.List;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Set;

public class RiempiSettimana {
	public static void riempiSettimana(List res, String nomevia) throws Exception {
		// TODO Auto-generated method stub
		  int nautot=0,datap = 0;
		  String[] vectday = null,daytime = null;
		  boolean inizio=true;
		  
		  
		  List lista = new List();
		  for(int i=0; i<res.getItemCount();++i) {
			  String s = res.getItem(i);
			  String[] v = s.split("\\,");
			  daytime= v[2].split("\\ ");
			  vectday=daytime[0].split("\\/");
			  
			  
			  if (inizio) { 
				  datap=Integer.parseInt(vectday[2]);
			  }
			  inizio=false;
			  
			  
			  
			  if(datap!=Integer.parseInt(vectday[2])) {
				  
				  lista.add(nautot + "£" + datap );
                  nautot=0;
				  datap=Integer.parseInt(vectday[2]);
			  }
			  
				  nautot += Integer.parseInt(v[1]);
			  
			  
		  }
		  
		  lista.add(nautot + "£" + datap);
		  GraficoLinee grafico = new GraficoLinee("Grafico", nomevia,"Giorni","Numero Auto", lista);
	  } 
}
