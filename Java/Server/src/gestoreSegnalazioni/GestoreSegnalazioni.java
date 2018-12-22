package gestoreSegnalazioni;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import creatoreMappa.Mappa;
import creatoreMappa.Strada;
import shared.IntSegnalazioni;

public class GestoreSegnalazioni extends UnicastRemoteObject implements IntSegnalazioni{
	protected GestoreSegnalazioni() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private static GestoreSegnalazioni instance = null;
	
	public static GestoreSegnalazioni getIstance() throws RemoteException {
		if(instance == null) {
			instance = new GestoreSegnalazioni();
		}
		return instance;
	}
	
	public boolean inviaSegnalazione(char tipo_traffico, String posizione) throws RemoteException{
		Mappa.getIstance().segnalazione(posizione, tipo_traffico);
		return true;
	}
	
	public String notifica(String posizione) {
		return Mappa.getIstance().action(posizione);
	}
	
	public Set<String> riceviNodoVicino(String posizione) {
		Set<String> vettore = new HashSet<String>();
		Iterator<Strada> i = Mappa.getIstance().getNodi().get(Mappa.getIstance().riferimento(posizione)).getLista().iterator();
		while(i.hasNext()) {
			Strada s = i.next();
			vettore.add(s.getVia() + " " +s.getNode1().getCoordinate() + " " + s.getNode2().getCoordinate());
		}
		return vettore;
	}
}