package gestoreAccessoSistema;

import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import gestoreAccessoDatabase.GestoreAccessoDatabase;
import gestoreDirettive.GestoreDirettive;
import shared.IntACC;
import shared.IntDirettiva;

public class GestoreAccessoSistema extends UnicastRemoteObject implements IntACC{
	
	private static GestoreAccessoSistema instance = null;

	public static GestoreAccessoSistema getIstance() throws RemoteException{
		if(instance == null) {
			instance = new GestoreAccessoSistema();
		}
		return instance;
	}

	protected GestoreAccessoSistema() throws RemoteException {
		super();
	}

	@Override
	public void aggiungiUtente(String nome, String cognome, String nickname, String password, int isAdmin) throws RemoteException {
		// TODO Auto-generated method stub
		GestoreAccessoDatabase.getIstance().setCredenziali(nome, cognome, nickname, password, isAdmin);
	}

	@Override
	public int esisteUtente(String nickname) throws RemoteException {
		// TODO Auto-generated method stub
		return GestoreAccessoDatabase.getIstance().controlloNick(nickname);
	}
	@Override
	public boolean credValide(String nickname, String password) throws RemoteException {
		String[] vettore;
		vettore = GestoreAccessoDatabase.getIstance().getCredenziali(nickname);
		if(vettore == null) {
			System.out.println(nickname + password);
			return false;
		}
		if(vettore[3].equals(password)) {
			return true;
		}
		return false;
	}

	@Override
	public String[] getCredenziali(String nickname) throws RemoteException {
		return GestoreAccessoDatabase.getIstance().getCredenziali(nickname);
	}

	@Override
	public boolean modificaPassword(String nickname, String password) throws RemoteException {
		// TODO Auto-generated method stub
		return GestoreAccessoDatabase.getIstance().modificaPassword(nickname, password);
	}

	@Override
	public boolean eliminaUtente(String nickname) throws RemoteException {
		// TODO Auto-generated method stub
		return GestoreAccessoDatabase.getIstance().eliminaUtente(nickname);
	}

}

