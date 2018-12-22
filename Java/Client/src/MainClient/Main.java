package MainClient;

import utente.*;
import graficaUtente.*;
import shared.IntACC;
import shared.IntAggiornamento;
import shared.IntDirettiva;
import shared.IntMappa;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.SQLException;
import java.util.Set;

public class Main {
	
	public static void main(String[] args) throws NotBoundException, IOException {
		// TODO Auto-generated method stub
		LogIn finestra = new LogIn();
		finestra.setVisible(true);
	}		
}
