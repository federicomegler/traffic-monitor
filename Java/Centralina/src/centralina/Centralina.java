package centralina;

import shared.IntCentralina;
import shared.IntDirettiva;
import centralina.ValoreParametri;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
public class Centralina extends UnicastRemoteObject implements Runnable, IntDirettiva{
	private static Vector<Thread> lista_thread = new Vector<Thread>();
	private static Vector<Centralina> lista_centraline = new Vector<Centralina>();
	private static Timer timer = new Timer();
	private ValoreParametri valoreparametri;
	private TimerTask task;
	
	public Centralina(String nome_via,int id_centralina,int numero_veicoli_limite,
            int limite_velocità,String inizio, String fine) throws RemoteException {
		
		  this.valoreparametri =  new ValoreParametri (nome_via, id_centralina, numero_veicoli_limite, limite_velocità, inizio, fine);  
	}
	
	public Centralina() throws RemoteException{
		// TODO Auto-generated constructor stub
	}
	
	public void setAggiornamento(ValoreParametri nuovivalori) {
		
		this.valoreparametri.aggiornamentoParametri(nuovivalori);
		
	}
	
	
	
	public ValoreParametri getValoriParametri() {
		return this.valoreparametri;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			rileva();
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public void rileva() throws MalformedURLException, RemoteException, NotBoundException {
		
		task = new TimerTask() {
			
			@Override
			//estrae valori di velocità
			public void run() {
				Random estrattore = new Random();
				int n_auto = estrattore.nextInt(valoreparametri.getNumero_veicoli_limite() + (int) (valoreparametri.getNumero_veicoli_limite()*0.4));
				int[] velocita;
				velocita = new int[n_auto];
				for(int i = 0; i < n_auto; ++i) {
					velocita[i] = estrattore.nextInt(valoreparametri.getLimite_velocità() + (int) (valoreparametri.getLimite_velocità()*0.25));
				}
				int media = 0;
				
				//calcolo la velocita media
				for(int j=0; j<n_auto; ++j) {
					media = media + velocita[j];
				}
				if(n_auto == 0)
					media = 0;
				else
					media = media/n_auto;
				
				valoreparametri.setMedia(media);
				valoreparametri.setN_veicoli(n_auto);
				
				IntCentralina server;
				try {
					server = (IntCentralina) Naming.lookup("rmi://localhost:12345/DATISTRADALI");
					server.salvaDatiStradali(valoreparametri.getId_centralina(), valoreparametri.getInizio(), valoreparametri.getFine(),
			                 valoreparametri.getMedia(), valoreparametri.getN_veicoli());
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};		
		timer.scheduleAtFixedRate(task, 60*1000, 60*1000);
	}
	
	public void nuovaCentralina(String nome_via, String inizio, String fine, int id_centralina, int numero_veicoli_limite,int limite_velocita) throws RemoteException {
		Centralina nuova = new Centralina(nome_via, id_centralina, numero_veicoli_limite, limite_velocita, inizio, fine);
		Thread t = new Thread(nuova);
		lista_thread.addElement(t);
		lista_centraline.addElement(nuova);
		t.start();
	}

	
	public void eliminaCentralina(int idcentralina) {
		for(int i=0; i<lista_centraline.size(); ++i) {
			if(idcentralina == lista_centraline.get(i).getValoriParametri().getId_centralina()) {
				lista_centraline.get(i).task.cancel();
				lista_centraline.remove(i);
				lista_thread.remove(i);
				break;
			}
		}
	}

	@Override
	public void modificaCentralina(int n_limite_auto, int limite_vel, String inizio, String fine) throws RemoteException {
		// TODO Auto-generated method stub
		String nome_via;
		int id_centralina;
		for(int i=0; i<lista_centraline.size(); ++i) {
			if(lista_centraline.get(i).getValoriParametri().getInizio().equals(inizio) &&
				lista_centraline.get(i).getValoriParametri().getFine().equals(fine)) {
				
				lista_centraline.get(i).task.cancel();
				nome_via = lista_centraline.get(i).getValoriParametri().getNome_via();
				id_centralina = lista_centraline.get(i).getValoriParametri().getId_centralina();
				nuovaCentralina(nome_via, inizio, fine, id_centralina, n_limite_auto, limite_vel);
				lista_centraline.remove(i);
				lista_thread.remove(i);
				break;
			}
		}
	}
}
