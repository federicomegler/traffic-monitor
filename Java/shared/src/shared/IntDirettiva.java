package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IntDirettiva extends Remote{
	public void nuovaCentralina(String nome_via, String inizio, String fine, int id_centralina, int numero_veicoli_limite,int limite_velocita) throws RemoteException;
	public void modificaCentralina(int n_limite_auto, int limite_vel, String inizio, String fine) throws RemoteException;
	void eliminaCentralina(int idcentralina) throws RemoteException;
}
