package creatoreMappa;
import java.math.*;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gestoreAccessoDatabase.GestoreAccessoDatabase;

import java.awt.Component;
import java.awt.Graphics;
import java.io.*;

public class Mappa  { //grafo con hashmap
	
	private HashMap<String,Nodo> nodi; //coordinate perché sono uniche per ogni incrocio
	private static Mappa instance = null;
	public static Mappa getIstance() {
		if(instance == null) {
			instance = new Mappa();
		}
		return instance;
	}
	
	
	protected Mappa () {
		
		nodi=new HashMap<String,Nodo>();
		try {
			riempimento();
		} catch (RemoteException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public HashMap<String,Nodo> getNodi(){
		
		return nodi;
	}
	  public void add (Nodo node1, Nodo node2, int distanza, String nomevia, String idcentralina) {
		
		  if(!nodi.containsKey(node1.getCoordinate())) 
			  nodi.put(node1.getCoordinate(), node1); 
		  //controllo se è già presente la chiave nel caso l'aggiungo nella hashmap  	     
          if(!nodi.containsKey(node2.getCoordinate())) 
        	  nodi.put(node2.getCoordinate(), node2); 
			//creo un nuovo oggetto strada e lo aggiungo alla lista dei nodi  
          Strada tratto=new Strada(node1,node2,nomevia,distanza,idcentralina);
              nodi.get(node1.getCoordinate()).getLista().add(tratto);
              nodi.get(node2.getCoordinate()).getLista().add(tratto);
		  
	  }

public int eliminaCentralina(String coordinate1, String coordinate2) {
	int centralina=-1;
	Nodo nodo = nodi.get(coordinate1);
	Iterator<Strada> it= nodo.getLista().iterator();
	Strada tratto;
	while(it.hasNext()) {
		tratto = it.next();
		if(tratto.getNode2().getCoordinate().equals(coordinate2)) {
			System.out.println(tratto.getId_centralina());
			centralina = Integer.parseInt(tratto.getId_centralina());
			tratto.setId_centralina(null);
			tratto.setStato('R');
		}
	}
	return centralina;
}
	  
	public String riferimento(String coordinate) {
	String rif=null;
    double latitudinenodo,longitudinenodo,latitudinepos,longitudinepos,distanza_utente=100000;
	Iterator<String> it=nodi.keySet().iterator();	
	String s[]=coordinate.split("\\£" );
	latitudinepos=Double.parseDouble(s[0]);
	longitudinepos=Double.parseDouble(s[1]);
	
	while(it.hasNext()) {
    String temp=it.next();
    
	latitudinenodo=nodi.get( temp ).getY();
	longitudinenodo= nodi.get( temp ).getX();
	//prendo il nodo più vicino all'utente come riferimento	
	if((Math.sqrt(   (latitudinenodo-latitudinepos)*(latitudinenodo-latitudinepos)+(longitudinenodo-longitudinepos)*(longitudinenodo-longitudinepos) )) < distanza_utente) 				
			{rif=(nodi.get( temp ).getCoordinate());
	
	        distanza_utente=(Math.sqrt(   (latitudinenodo-latitudinepos)*(latitudinenodo-latitudinepos)+(longitudinenodo-longitudinepos)*(longitudinenodo-longitudinepos) ));

			}
	 }
	
	return rif;
	
}

	  public String action(String coordinate) { 
        
	    String rif = riferimento(coordinate);
		HashMap<String,String> strade =new HashMap<String,String>();
		int distanza=0;
		controllo (strade,distanza,rif) ;
		Iterator<String> i=strade.keySet().iterator();
		String l[];
		String s = new String();
		while(i.hasNext()) {
			String tep=i.next();
			l= strade.get(tep).split("\\ ");
			s += "Traffico in "+tep+" con tipo traffico "+l[0]+" a circa "+l[1]+" metri dal prossimo incrocio.\n\n";
		}
		return s;	
	}
	 
	  public void segnalazione(String nomeecoord,char stato) {
		  //via nome 500 500 700 700
		  String []via=nomeecoord.split("\\ ");
		  
		Iterator<Strada> it=nodi.get(riferimento(via[2]+"£"+via[3])).getLista().iterator();
        Strada tratto;

        while(it.hasNext()){
     	   tratto=it.next();
     	   if(tratto.getVia().equals((via[0]+" "+via[1])) && tratto.getNode1().getCoordinate().equals(via[2]+"£"+via[3])
     			   && tratto.getNode2().getCoordinate().equals((via[4]+"£"+via[5]))) 
     		   tratto.setStato(stato); 
        }
	  }
	  

		
      public void controllo (HashMap<String,String> strade,int distanza,String root) {
    //visito il  nodo 
    	  if(nodi.get(root).isMark() || distanza>1000) return; //condizione di uscita
    	  nodi.get(root).setMark(true); 
    	  //sto visitando il nodo
    	  Iterator<Strada> it=nodi.get(root).getLista().iterator(); //voglio avere la lista delle strade 
    	  
    	 //la itero tutta e vedo com'è la situazione del traffico per ogni via 
    	  while(it.hasNext()) {
           Strada temp=it.next(); 
    		  if(temp.getStato()!='R') { // se la situa è diversa da regolare 
    			
        	  if(!(strade.containsKey(  temp.getVia() ) ))
               		  strade.put( temp.getVia() ,Character.toString(temp.getStato())+" "+Integer.toString(distanza));
        	  
        	  else {
         
        		  String s[]=(strade.get(temp.getVia() )).split("\\ ");
        		  
        		  if( s[0].charAt(0)>temp.getStato() || (Integer.parseInt(s[1]) > distanza &&  s[0].charAt(0)==temp.getStato()) ) {
        		 
        		  strade.replace(temp.getVia(), strade.get(temp.getVia()),
        				  temp.getStato()+" "+Integer.toString(distanza));
        		  	
        		  }
        		 				  
        	  }
        	  
          }          
    		controllo(strade,distanza+(temp.getLunghezza()),temp.getNode1().getCoordinate()); 
          
        	controllo(strade,distanza+(temp.getLunghezza()),temp.getNode2().getCoordinate()); 
                
    	  
    	  } 
    	  
    	  nodi.get(root).setMark(false);
      }
	     
      public void riempimento() throws  SQLException, RemoteException  {
    	 
    	  ResultSet result = GestoreAccessoDatabase.getIstance().ottieniVie();

    	  while(result.next()) {

        	  this.add(new Nodo(result.getString("inizio")), new Nodo(result.getString("fine")), result.getInt("distanza"), result.getString("nome_via"),result.getString("idcentralina"));
    	  }
      }  
      
      public void setStatoVia(String inizio, String fine, char stato) {
    	  Nodo nodo = nodi.get(inizio);
    	  Strada strada;
    	  Iterator<Strada> it = nodo.getLista().iterator();
    	  while(it.hasNext()) {
    		  strada = it.next();
    		  if(strada.getNode1().getCoordinate().equals(inizio)) {
    			  if(strada.getNode2().getCoordinate().equals(fine)) {
    				  strada.setStato(stato);
    			  }
    		  }
    	  }
      }

}


