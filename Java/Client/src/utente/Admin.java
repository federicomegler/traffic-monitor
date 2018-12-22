package utente;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import shared.IntAggiornamento;
import utente.Utente;

public class Admin extends Utente {
	
	public Admin(String nome, String cognome, String nickname, String password, int isAdmin) {
		// TODO Auto-generated constructor stub
		super(nome,cognome,nickname,password,isAdmin);
	}

	public static boolean inviaAggiornamento(String posizione, int limite_auto, int limite_velocita, String inizio, String fine) throws MalformedURLException, RemoteException, NotBoundException {
		IntAggiornamento aggiornamento = (IntAggiornamento) Naming.lookup("rmi://localhost:12345/DIRETTIVE");
		try {
			return aggiornamento.creaCentralina(posizione, inizio, fine, limite_auto, limite_velocita);
		}
		catch(RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean modificaCentralina(int n_limite_auto, int limite_vel, String inizio, String fine) {
		try {
			IntAggiornamento server = (IntAggiornamento)Naming.lookup("rmi://localhost:12345/DIRETTIVE");
			return server.modificaCentralina(n_limite_auto, limite_vel, inizio, fine);
		} 
		catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
public static void eliminaCentralina (String coordinate1 , String coordinate2) {
		
		try {
			IntAggiornamento dir = (IntAggiornamento) Naming.lookup("rmi://localhost:12345/DIRETTIVE");
			dir.eliminaCentralina(coordinate1,coordinate2);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
