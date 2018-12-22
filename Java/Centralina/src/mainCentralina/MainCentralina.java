package mainCentralina;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import centralina.Centralina;
import java.lang.Object;
import java.util.Vector;

import shared.IntCentralina;
import shared.IntDirettiva;

public class MainCentralina {
	
	public static void main(String[] args) throws RemoteException{
	
		Centralina server = new Centralina();
		System.setProperty("java.security.policy", System.getProperty("user.dir") + "\\src\\grantFile.policy");
		Registry reg = LocateRegistry.createRegistry(12344);
		System.setSecurityManager(new SecurityManager());
		try {
			Naming.rebind("rmi://localhost:12344/CENTRALINA", (IntDirettiva)server);
			IntCentralina creatore = (IntCentralina) Naming.lookup("rmi://localhost:12345/DATISTRADALI");
			creatore.ottieniCentraline();
		}
		catch(RemoteException e) {
			e.printStackTrace();
		}
		catch(MalformedURLException e) {
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
}

