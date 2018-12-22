package graficaUtente;
import javax.swing.JFrame;
import javax.swing.JPanel;

import MainClient.Main;
import shared.IntMappa;

import java.awt.Graphics;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Mappa extends JPanel{
	private static Mappa instance = null;
	public static Mappa getIstance() {
		if (instance == null) {
			instance = new Mappa();
		}
		return instance;
	}
	public static void main() throws NotBoundException, SQLException, MalformedURLException, RemoteException {
		Mappa mappa = Mappa.getIstance();
		mappa.setLinee(mappa.getLineeServer());
		JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(800, 800);
        jFrame.setVisible(true);
        jFrame.add(mappa);
	}
	
	
	private Set<Line> linee;
	
	public Mappa() {
	}
	
	public Set<Line> getLinee(){
		return linee;
	}
	
	//se aggiornamento == true ricevo tutti i singoli segmenti
	public Set<String> ottieniVie() {
		boolean t = true;
		Set<String> vettore = new HashSet<String>();
		Iterator<Line> i = linee.iterator();
		Iterator<String> it = vettore.iterator();
		Line linea;
		
		while(i.hasNext()) {
			linea = i.next();
			
				while(it.hasNext()) {

					if(linea.getNome().equals(it.next())) 
						t = false;
					else
						t = true;
				}
			

			if(t)
				vettore.add(linea.getNome());

			it=vettore.iterator();
		}	
		return vettore;
	}
	
	public void setVia(String via) {
		Iterator<Line> i = linee.iterator();
		Line linea;
		while(i.hasNext()) {
			linea = i.next();
			if(via.equals(linea.getNome())) {
				linea.setSelected(true);
			}
			else
				linea.setSelected(false);
		}
		repaint();
	}
	
	public void setLinee(Set<String> vettore) {
		Iterator<String> it = vettore.iterator();
		linee = new HashSet<Line>();
		while(it.hasNext()) {
			Line a = new Line(it.next());
			linee.add(a);
		}
		repaint();
	}
	
	public Set<String> getLineeServer() throws NotBoundException, SQLException, MalformedURLException, RemoteException{
		IntMappa server = (IntMappa) Naming.lookup("rmi://localhost:12345/MAPPA");
		return server.generaLinee();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Iterator<Line> i = linee.iterator();
		Line linea;
		while(i.hasNext()) {
			linea = i.next();
			linea.paintBack(g);
		}		
		i = linee.iterator();
		while(i.hasNext()) {
			linea = i.next();
			linea.paintStreet(g);
		}		
		i = linee.iterator();
		while(i.hasNext()) {
			linea = i.next();
			linea.paintStatus(g);
		}		
	}
	
}
