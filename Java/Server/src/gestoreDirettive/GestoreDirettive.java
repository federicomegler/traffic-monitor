package gestoreDirettive;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;

import creatoreMappa.Mappa;
import creatoreMappa.Nodo;
import creatoreMappa.Strada;
import gestoreAccessoDatabase.GestoreAccessoDatabase;
import shared.IntAggiornamento;
import shared.IntDirettiva;

public class GestoreDirettive extends UnicastRemoteObject implements IntAggiornamento {
	
	private static GestoreDirettive instance = null;
	
	public static GestoreDirettive getIstance() throws RemoteException{
		if(instance == null) {
			instance = new GestoreDirettive();
		}
		return instance;
	}
	
	protected GestoreDirettive() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void eliminaCentralina(String coordinate1, String coordinate2) {
		int idcentralina = Mappa.getIstance().eliminaCentralina(coordinate1, coordinate2);
		if(idcentralina!=-1) {
			
			try {
				GestoreAccessoDatabase.getIstance().eliminaCentralina(idcentralina);
				IntDirettiva server = (IntDirettiva) Naming.lookup("rmi://localhost:12344/CENTRALINA");
				server.eliminaCentralina(idcentralina);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void modificaCentralina() {
		try {
			IntDirettiva server = (IntDirettiva) Naming.lookup("rmi://localhost:12344/CENTRALINA");
		} 
		catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean modificaCentralina(int n_limite_auto, int limite_vel, String inizio, String fine) {
		boolean controllo = false;
		try {
			controllo = GestoreAccessoDatabase.getIstance().modificaDatiCentralina(n_limite_auto, limite_vel, inizio, fine);
			if(controllo) {
				try {
					IntDirettiva server = (IntDirettiva)Naming.lookup("rmi://localhost:12344/CENTRALINA");
					server.modificaCentralina(n_limite_auto, limite_vel, inizio, fine);
				}
				catch (MalformedURLException | NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					controllo = false;
				}
			}
			return controllo;
		}
		catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean creaCentralina( String nome_via,String inizio, String fine, int n_auto_limite, int velocita_limite) throws MalformedURLException, RemoteException, NotBoundException {
		IntDirettiva server = (IntDirettiva) Naming.lookup("rmi://localhost:12344/CENTRALINA");
		Nodo node=Mappa.getIstance().getNodi().get(inizio);
	    Iterator<Strada> it = node.getLista().iterator(); 
	    Strada tratto;
	    int idcentralina;
	    while(it.hasNext()) {
	    	tratto=it.next();
	    	if(tratto.getNode2().getCoordinate().equals(fine)) {	
	    		if(tratto.getId_centralina()!=null)
	    			return false;
	    		
	    		break;
	    	}	
	    }
	    
		
		try {
			idcentralina = GestoreAccessoDatabase.getIstance().ottieniMaxIDCentralina();
			server.nuovaCentralina(nome_via,inizio,fine,idcentralina,n_auto_limite,velocita_limite);
			GestoreAccessoDatabase.getIstance().salvaNuovaCentralina(idcentralina, inizio, fine, velocita_limite, n_auto_limite, nome_via);
			GestoreAccessoDatabase.getIstance().aggiornaStrada(idcentralina, inizio, fine);
			   it = node.getLista().iterator(); 
			    while(it.hasNext()) {
			    	tratto=it.next();
			    	if(tratto.getNode2().getCoordinate().equals(fine)) {	
			    		tratto.setId_centralina(Integer.toString(idcentralina));
			    	}	
			    }
			return true;
		}
		catch(RemoteException e) {
			e.printStackTrace();
			return false;
		}
		

	
	}
	

	public void creaCentralina(int idCentralina, String nome_via,String inizio, String fine, int n_auto_limite, int velocita_limite) throws MalformedURLException, RemoteException, NotBoundException {
		IntDirettiva server = (IntDirettiva) Naming.lookup("rmi://localhost:12344/CENTRALINA");
		try {
			
			server.nuovaCentralina(nome_via,inizio,fine,idCentralina,n_auto_limite,velocita_limite);
		}
		catch(RemoteException e) {
			e.printStackTrace();
		}
	}
		
}
