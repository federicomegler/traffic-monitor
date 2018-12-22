package gestoreStatistiche;

import java.awt.List;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import gestoreAccessoDatabase.GestoreAccessoDatabase;
import shared.IntStatistiche;

public class CreatoreStatistiche extends UnicastRemoteObject implements IntStatistiche{
	private static CreatoreStatistiche instance = null;

	protected CreatoreStatistiche() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static CreatoreStatistiche getIstance() throws RemoteException {
		if(instance == null){
			instance = new CreatoreStatistiche();
		}
		return instance;
	}
	//ottengo: velocita media, numero di auto, data, tipo traffico
	public List ottieniDatiStatistiche(String inizio, String fine) throws RemoteException {
		List set = new List();
		ResultSet ris = GestoreAccessoDatabase.getIstance().ottieniDatiStats(inizio, fine);
		try {
			while(ris.next()) {
				set.add(ris.getInt("velocita_media") + "," + ris.getInt("numero_auto")+","+ris.getString("data")+","+ris.getString("tipo"));
			}
			return set;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
	}

}
