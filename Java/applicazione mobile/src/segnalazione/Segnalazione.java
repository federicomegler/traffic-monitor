package segnalazione;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Set;

import shared.IntACC;
import shared.IntInfoTraffico;
import shared.IntSegnalazioni;

public class Segnalazione extends UnicastRemoteObject implements IntInfoTraffico {
	private String posizione;
	public Segnalazione() throws RemoteException {
		super();
		posizione = getPosizione();
		// TODO Auto-generated constructor stub
	}

	public static boolean inviaSegnalazione(char tipo_traffico, String posizione) throws MalformedURLException, RemoteException, NotBoundException {
		boolean controllo = false;
		IntSegnalazioni server = (IntSegnalazioni) Naming.lookup("rmi://localhost:12345/SEGNALAZIONI");
		try {
			controllo =  server.inviaSegnalazione(tipo_traffico, posizione);
			return controllo;
		}
		catch(RemoteException e) {
			e.printStackTrace();
			return controllo;
		}
	}
	
	public String riceviNotifica(String posizione) {
		try {
			IntSegnalazioni server = (IntSegnalazioni)Naming.lookup("rmi://localhost:12345/SEGNALAZIONI");
			return server.notifica(posizione);
		}
		catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Set<String> riceviStrade(String posizione){
		try {
			IntSegnalazioni server = (IntSegnalazioni)Naming.lookup("rmi://localhost:12345/SEGNALAZIONI");
			return server.riceviNodoVicino(posizione);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public String getPosizione() {
		String posizione;
		Random estrattore = new Random();
		int x = estrattore.nextInt(4000);
		int y = estrattore.nextInt(4000);
		posizione = new String(Integer.toString(y)+"£"+Integer.toString(x));
		return posizione;
	}

	public void setPosizione(String posizione) {
		this.posizione = posizione;
	}
}
