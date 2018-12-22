package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IntACC extends Remote{
	public void aggiungiUtente(String nome, String cognome, String nickname, String password, int isAdmin) throws RemoteException;
	public int esisteUtente(String nickname) throws RemoteException;
	public boolean credValide(String nickname, String password) throws RemoteException;
	public String[] getCredenziali(String nickname) throws RemoteException;
	public boolean modificaPassword(String nickname,String password) throws RemoteException;
	public boolean eliminaUtente(String nickname)throws RemoteException;
}
