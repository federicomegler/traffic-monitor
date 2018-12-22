package utente;

import utente.Credenziali;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import graficaUtente.*;
import shared.IntACC;

public class Utente {
	private Credenziali credenziali;
	private static Utente utente = null;
	
	protected Utente() {}
	
	public static Utente getUtente() {
		if(utente == null) {
			utente = new Utente();
		}
		return utente;
	}
	
	public Utente(String nome, String cognome, String nickname, String password, int isAdmin) {
		setCredenziali(new Credenziali(nome, cognome, nickname, password,isAdmin));
	}

	public Credenziali getCredenziali() {
		return credenziali;
	}

	public void setCredenziali(Credenziali credenziali) {
		this.credenziali = credenziali;
	}	
	
	public static String[] ottieniCredenziali(String nickname) throws MalformedURLException, RemoteException, NotBoundException {
		IntACC server = (IntACC)Naming.lookup("rmi://localhost:12345/ACCESSO");
		return server.getCredenziali(nickname);		
	}
	
	public static void setUtente(String nome, String cognome, String nickname, String password, int isAdmin) {
		if(isAdmin == 1) {
			utente = new Admin(nome,cognome,nickname,password,isAdmin);
		}
		else {
			utente = new Utente(nome,cognome,nickname,password,isAdmin);
		}
	}
	
	public static boolean credenzialiValide(String nickname, String password) throws MalformedURLException, RemoteException, NotBoundException {
		IntACC server = (IntACC)Naming.lookup("rmi://localhost:12345/ACCESSO");
		try {
			return server.credValide(nickname, password);
		}
		catch(RemoteException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public static int creaUtente(String nome, String cognome, String nickname, String password, int isAdmin) throws MalformedURLException, RemoteException, NotBoundException {
		IntACC server = (IntACC) Naming.lookup("rmi://localhost:12345/ACCESSO");
		int controllo;
		try {		
			controllo = server.esisteUtente(nickname);
			if(controllo == 1) {
				server.aggiungiUtente(nome, cognome, nickname, password, isAdmin);
			}
		}
		catch(RemoteException e) {
			e.printStackTrace();
			return -1;
		}
		return controllo;
	}
	
	public static boolean modificaPassword(String nickname, String password) throws MalformedURLException, RemoteException, NotBoundException {
		IntACC server = (IntACC) Naming.lookup("rmi://localhost:12345/ACCESSO");
		try {
			server.modificaPassword(nickname, password);
			return true;
		}
		catch(RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean eliminaUtente(String nickname) throws MalformedURLException, RemoteException, NotBoundException {
		IntACC server = (IntACC) Naming.lookup("rmi://localhost:12345/ACCESSO");
		try {
			return server.eliminaUtente(nickname);
		}
		catch(RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
