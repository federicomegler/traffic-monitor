package segnalazione;
import java.awt.Color;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Grafica.App;
import shared.IntSegnalazioni;

public class MainApp {
	public static Segnalazione segnalazione;
	public static void main(String[] args) throws RemoteException {
		// TODO Auto-generated method stub
		segnalazione = new Segnalazione();
		Segnalazione app = new Segnalazione();
		App.main(null);
	}

}
