package shared;

import java.awt.List;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface IntStatistiche extends Remote{
	public List ottieniDatiStatistiche(String inizio, String fine) throws RemoteException;
}
