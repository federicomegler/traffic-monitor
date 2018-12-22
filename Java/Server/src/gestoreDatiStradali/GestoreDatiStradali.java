package gestoreDatiStradali;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import creatoreMappa.Mappa;
import gestoreAccessoDatabase.GestoreAccessoDatabase;
import gestoreDirettive.GestoreDirettive;
import shared.IntCentralina;

public class GestoreDatiStradali extends UnicastRemoteObject implements IntCentralina {
	private static GestoreDatiStradali instance = null;
	
	protected GestoreDatiStradali() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static GestoreDatiStradali getIstance() throws RemoteException {
		if(instance == null) {
			instance = new GestoreDatiStradali();
		}
		return instance;
	}

	@Override
	public void salvaDatiStradali(int id_centralina, String inizio, String fine, int velocita_media, int n_veicoli)
			throws RemoteException {
		// TODO Auto-generated method stub
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime ora = LocalDateTime.now();
		ResultSet ris = GestoreAccessoDatabase.getIstance().ottieniDatiCentralina(id_centralina);
		int limite_vel = 0;
		int lim_auto = 0;
		char tipo;
		try {
			while(ris.next()) {
				limite_vel = ris.getInt("limite_velocita");
				lim_auto = ris.getInt("limite_auto");
			}
			tipo = computaTraffico(n_veicoli, velocita_media,limite_vel, lim_auto);
			GestoreAccessoDatabase.getIstance().salvaDatiCentralina(id_centralina, inizio, fine, velocita_media, n_veicoli, format.format(ora),
					tipo);
			Mappa.getIstance().setStatoVia(inizio, fine, tipo);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public char computaTraffico(int veicoli_rilev, int vel_rilev, int limite_velocita, int n_limite_veicoli) {
		
		//controllo tipo intenso
		if(n_limite_veicoli/2 < veicoli_rilev) {
			if(limite_velocita/2 > vel_rilev) {
				return 'I';
			}
			else if(limite_velocita*0.8 > vel_rilev) {
				return 'M';
			}
		}
		return 'R';
	}

	@Override
	public void ottieniCentraline() throws RemoteException {
		// TODO Auto-generated method stub
		ResultSet ris = GestoreAccessoDatabase.getIstance().ottieniCentraline();
		try {
			while (ris.next()) {
				int id_centralina, n_auto_limite, velocita_limite;
				String nome_via, inizio, fine;
				id_centralina = ris.getInt("idcentralina");
				n_auto_limite = ris.getInt("limite_auto");
				velocita_limite = ris.getInt("limite_velocita");
				nome_via = ris.getString("nome_via");
				inizio = ris.getString("inizio");
				fine = ris.getString("fine");
				try {
					GestoreDirettive.getIstance().creaCentralina(id_centralina, nome_via, inizio, fine, n_auto_limite, velocita_limite);
				} catch (MalformedURLException | NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
