package shared;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IntAggiornamento extends Remote{
	public boolean creaCentralina(String nome_via,String inizio, String fine, int n_auto_limite, int velocita_limite) throws MalformedURLException, RemoteException, NotBoundException;
	public boolean modificaCentralina(int n_limite_auto, int limite_vel, String inizio, String fine) throws RemoteException;
	public void eliminaCentralina(String coordinate1, String coordinate2)throws RemoteException;
}
