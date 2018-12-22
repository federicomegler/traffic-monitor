package mainClasses;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import creatoreMappa.CreatoreMappa;

import gestoreAccessoDatabase.GestoreAccessoDatabase;
import gestoreAccessoSistema.GestoreAccessoSistema;
import gestoreDatiStradali.GestoreDatiStradali;
import gestoreDirettive.GestoreDirettive;
import gestoreSegnalazioni.GestoreSegnalazioni;
import gestoreStatistiche.CreatoreStatistiche;
import shared.IntACC;
import shared.IntAggiornamento;
import shared.IntCentralina;
import shared.IntDirettiva;
import shared.IntMappa;
import shared.IntSegnalazioni;
import shared.IntStatistiche;



public class MainSistema {

	public static void main(String[] args) throws RemoteException {
		// TODO Auto-generated method stub
		//oggetti da condividere
		
		GestoreDirettive direttive = GestoreDirettive.getIstance();
		GestoreAccessoSistema accesso = GestoreAccessoSistema.getIstance();
		GestoreSegnalazioni segnalazione = GestoreSegnalazioni.getIstance();
		GestoreDatiStradali datistradali = GestoreDatiStradali.getIstance();
		CreatoreStatistiche statistiche = CreatoreStatistiche.getIstance();
		CreatoreMappa mappa = CreatoreMappa.getistance();
		//rmi
		System.setProperty("java.security.policy", System.getProperty("user.dir") + "\\src\\grantFile.policy");
		Registry reg = LocateRegistry.createRegistry(12345);
		System.setSecurityManager(new SecurityManager());
		try {
			Naming.rebind("rmi://localhost:12345/DIRETTIVE", (IntAggiornamento)direttive);
			Naming.rebind("rmi://localhost:12345/ACCESSO", (IntACC)accesso);
			Naming.rebind("rmi://localhost:12345/SEGNALAZIONI", (IntSegnalazioni)segnalazione);
			Naming.rebind("rmi://localhost:12345/MAPPA", (IntMappa)mappa);
			Naming.rebind("rmi://localhost:12345/DATISTRADALI", (IntCentralina)datistradali);
			Naming.rebind("rmi://localhost:12345/STATISTICHE", (IntStatistiche)statistiche);
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		GestoreAccessoDatabase.getIstance().eliminaDatiStradali();
	}


}

