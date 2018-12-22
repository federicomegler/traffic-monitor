package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;

public interface IntCentralina extends Remote{
	public void salvaDatiStradali(int id_centralina, String inizio, String fine, int velocita_media, int n_veicoli) throws RemoteException;
	public void ottieniCentraline() throws RemoteException;
}
