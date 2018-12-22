package graficaUtente;
import java.util.SortedSet;
import java.util.TreeSet;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import MainClient.Main;
import utente.Admin;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.SpinnerListModel;
import java.awt.Toolkit;
import javax.swing.border.BevelBorder;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ImpostazioniCentralina extends JFrame {

	private JPanel contentPane;
	private JSpinner limiteVetture;
	private JSpinner limiteVelocita;
	private JComboBox<String> comboNuova;
	private JComboBox<String> comboModifica;
	private JLabel lblCentralinaGiEsistente;
	private JSpinner lim_vel;
	private JSpinner lim_auto;
	private JButton btnElimina;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImpostazioniCentralina frame = new ImpostazioniCentralina();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ImpostazioniCentralina() {
		this.setResizable(false);
		Mappa mappa = Mappa.getIstance();
		setMaximumSize(new Dimension(700, 300));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ImpostazioniCentralina.class.getResource("/Resources/IconaSemaforo.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 700, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNuovaCentralina = new JLabel("Nuova centralina:");
		lblNuovaCentralina.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNuovaCentralina.setBounds(10, 11, 130, 30);
		contentPane.add(lblNuovaCentralina);
		
		JLabel lblNomeVia = new JLabel("Nome via:");
		lblNomeVia.setBounds(10, 70, 130, 14);
		contentPane.add(lblNomeVia);
		
		JLabel lblLimiteVetture = new JLabel("Limite vetture:");
		lblLimiteVetture.setBounds(10, 95, 130, 14);
		contentPane.add(lblLimiteVetture);
		
		JLabel lblLimiteVelocit = new JLabel("Limite velocit\u00E0:");
		lblLimiteVelocit.setBounds(10, 120, 130, 14);
		contentPane.add(lblLimiteVelocit);
		
		limiteVetture = new JSpinner();
		limiteVetture.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(10)));
		limiteVetture.setBounds(150, 92, 80, 20);
		contentPane.add(limiteVetture);
		
		JButton btnCrea = new JButton("Crea");
		btnCrea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean controllo = false;
				try {
					String s = (String) comboNuova.getSelectedItem();
					String[] vett = s.split("\\ ");
					controllo = Admin.inviaAggiornamento(vett[0]+" "+vett[1], Integer.parseInt(limiteVetture.getValue().toString()), 
							             Integer.parseInt(limiteVelocita.getValue().toString()), vett[2]+"£"+vett[3], vett[4]+"£"+vett[5]);
				} catch (NumberFormatException | MalformedURLException | RemoteException | NotBoundException e1) {
					e1.printStackTrace();
				}
				if(!controllo) {
					lblCentralinaGiEsistente.setVisible(true);
				}
				else {
					try {
						Mappa.getIstance().setLinee(Mappa.getIstance().getLineeServer());
					} 
					catch (MalformedURLException | RemoteException | NotBoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					PaginaUtente.main(true);
					PaginaUtente.getInternalMappa().setVisible(false);
					PaginaUtente.getInternalMappa().setVisible(true);
					dispose();
				}	
			}
		});
		btnCrea.setBounds(150, 148, 89, 23);
		contentPane.add(btnCrea);
		
		JLabel lblVia = new JLabel("via:");
		lblVia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVia.setBounds(103, 70, 46, 14);
		contentPane.add(lblVia);
		
		limiteVelocita = new JSpinner();
		limiteVelocita.setModel(new SpinnerListModel(new String[] {"30", "50", "90"}));
		limiteVelocita.setBounds(150, 117, 80, 20);
		contentPane.add(limiteVelocita);
		
		JLabel lblKmh = new JLabel("Km/h");
		lblKmh.setBounds(235, 120, 46, 14);
		contentPane.add(lblKmh);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(350, 11, 324, 239);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Modifica centralina:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 11, 180, 30);
		panel.add(lblNewLabel);
		
		JLabel lblIdcentralina = new JLabel("Posizione centralina:");
		lblIdcentralina.setBounds(10, 52, 150, 14);
		panel.add(lblIdcentralina);
		
		JLabel lblNuovoLimiteVetture = new JLabel("Nuovo limite vetture:");
		lblNuovoLimiteVetture.setBounds(10, 77, 150, 14);
		panel.add(lblNuovoLimiteVetture);
		
		JLabel lblNuovoLimiteVelocit = new JLabel("Nuovo limite velocit\u00E0:");
		lblNuovoLimiteVelocit.setBounds(10, 102, 150, 14);
		panel.add(lblNuovoLimiteVelocit);
		
		lim_auto = new JSpinner();
		lim_auto.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(10)));
		lim_auto.setBounds(147, 74, 103, 20);
		panel.add(lim_auto);
		
		lim_vel = new JSpinner();
		lim_vel.setModel(new SpinnerListModel(new String[] {"30", "50", "90"}));
		lim_vel.setBounds(147, 99, 103, 20);
		panel.add(lim_vel);
		
		JLabel lblKmh_1 = new JLabel("Km/h");
		lblKmh_1.setBounds(251, 102, 46, 14);
		panel.add(lblKmh_1);
		
		JButton btnModifica = new JButton("Modifica");
		btnModifica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String[] vettore;
				String s = (String)comboModifica.getSelectedItem();
				vettore = s.split("\\ ");
				boolean controllo = false;
				controllo = Admin.modificaCentralina(Integer.parseInt(lim_auto.getValue().toString()), Integer.parseInt(lim_vel.getValue().toString()),
						vettore[2]+"£"+vettore[3], vettore[4]+"£"+vettore[5]);
				if(controllo) {
					PaginaUtente.main(true);
					dispose();
				}
			}
		});
		btnModifica.setBounds(147, 147, 89, 23);
		panel.add(btnModifica);
		
		comboModifica = new JComboBox<String>();
		comboModifica.setBounds(147, 49, 150, 20);
		panel.add(comboModifica);
		
		btnElimina = new JButton("Elimina");
		btnElimina.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String[] s = comboModifica.getSelectedItem().toString().split("\\ ");
				Admin.eliminaCentralina(s[2] + "£" + s[3], s[4] +"£" + s[5]);
				try {
					Mappa.getIstance().setLinee(Mappa.getIstance().getLineeServer());
				} 
				catch (MalformedURLException | RemoteException | NotBoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				PaginaUtente.main(true);
				dispose();
			}
		});
		btnElimina.setBounds(10, 205, 89, 23);
		panel.add(btnElimina);
		JLabel lblEliminaLaCentralina = new JLabel("Elimina la centralina selezionata!");
		lblEliminaLaCentralina.setBounds(109, 214, 205, 14);
		panel.add(lblEliminaLaCentralina);
		
		SortedSet<Line> vettore = new TreeSet<Line>();
		
		Line linea;
		Iterator<Line> j = mappa.getLinee().iterator();
		while(j.hasNext()) {
			vettore.add(j.next());
		}
		
		comboNuova = new JComboBox<String>();
		comboNuova.setBounds(150, 67, 190, 20);
		contentPane.add(comboNuova);
		
		lblCentralinaGiEsistente = new JLabel("Centralina gi\u00E0 esistente!");
		lblCentralinaGiEsistente.setVisible(false);
		lblCentralinaGiEsistente.setForeground(Color.RED);
		lblCentralinaGiEsistente.setBounds(150, 182, 141, 14);
		contentPane.add(lblCentralinaGiEsistente);
		
		JButton btnHomePage = new JButton("Home page");
		btnHomePage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				PaginaUtente.main(true);
				dispose();
			}
		});
		btnHomePage.setBounds(10, 227, 115, 23);
		contentPane.add(btnHomePage);
		Iterator<Line> i = vettore.iterator();
		while(i.hasNext()) {
			linea = i.next();
			
			if(linea.isPresenza()) 				
				comboModifica.addItem(linea.getNome() + " " + linea.getY1() + " " + linea.getX1() +" " + linea.getY2() + " "  + linea.getX2());
			
			comboNuova.addItem(linea.getNome() + " " + linea.getY1() + " " + linea.getX1() +" " + linea.getY2() + " "  + linea.getX2());
		}
	}
}
