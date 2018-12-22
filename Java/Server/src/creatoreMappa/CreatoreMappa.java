package creatoreMappa;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import shared.IntMappa;

public class CreatoreMappa extends UnicastRemoteObject implements IntMappa{
	protected CreatoreMappa() throws RemoteException {
		super();
		Mappa.getIstance();
		// TODO Auto-generated constructor stub
	}

	private static CreatoreMappa instance = null;
	
	public static CreatoreMappa getistance() throws RemoteException {
		if(instance == null) {
			instance = new CreatoreMappa();
		}
		return instance;
	}
	
	@Override
	public Set<String> generaLinee() throws RemoteException, SQLException{
		Set<String> linee = new HashSet<String>();
		Mappa mappa = Mappa.getIstance();
		Iterator<String> it = mappa.getNodi().keySet().iterator();
		Iterator<Strada> i;
		Nodo n;
		Strada s;
		while(it.hasNext()) {
			n = mappa.getNodi().get(it.next());
			i = n.getLista().iterator();
			while(i.hasNext()) {
				s = i.next();
				String stringa;
				if(s.getId_centralina() != null)
					stringa = new String(s.getNode1().getCoordinate()+","+s.getNode2().getCoordinate()+","+s.getStato()+","+s.getVia()+","+"1");
				else
					stringa = new String(s.getNode1().getCoordinate()+","+s.getNode2().getCoordinate()+","+s.getStato()+","+s.getVia()+","+"0");
				linee.add(stringa);
			}
		}
		return linee;
	}
	
}
