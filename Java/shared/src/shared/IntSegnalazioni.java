package shared;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface IntSegnalazioni extends Remote{
	public boolean inviaSegnalazione(char tipo_traffico, String posizione) throws RemoteException;
	public Set<String> riceviNodoVicino(String posizione) throws RemoteException;
	public String notifica(String posizione) throws RemoteException;
}
