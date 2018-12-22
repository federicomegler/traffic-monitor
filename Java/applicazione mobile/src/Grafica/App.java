package Grafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import segnalazione.MainApp;
import segnalazione.Segnalazione;
import shared.IntSegnalazioni;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class App extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblErroreDiConnessione;
	private JRadioButton rdIntenso;
	private JRadioButton rdScorrevole;
	private JRadioButton rdAssente;
	private JButton btnInviaSegnalazione;
	private JLabel lblSelezionareIlTipo;
	private JComboBox<String> comboBox;
	private JButton btnNuovaSegnalazione;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
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
	public App() {
		setTitle("App Traffic Monitor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNotificheTraffico = new JLabel("Notifiche traffico:");
		lblNotificheTraffico.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNotificheTraffico.setBounds(10, 11, 135, 30);
		contentPane.add(lblNotificheTraffico);
		
		JLabel lblVerrUtilizzataLa = new JLabel("Verr\u00E0 utilizzata la posizione del GPS");
		lblVerrUtilizzataLa.setBounds(10, 35, 230, 14);
		contentPane.add(lblVerrUtilizzataLa);
		
		lblSelezionareIlTipo = new JLabel("Selezionare il tipo di traffico:");
		lblSelezionareIlTipo.setVisible(false);
		lblSelezionareIlTipo.setBounds(10, 96, 180, 14);
		contentPane.add(lblSelezionareIlTipo);
		
		rdAssente = new JRadioButton("Assente");
		rdAssente.setVisible(false);
		buttonGroup.add(rdAssente);
		rdAssente.setBounds(20, 117, 109, 23);
		contentPane.add(rdAssente);
		
		rdScorrevole = new JRadioButton("Scorrevole");
		rdScorrevole.setVisible(false);
		buttonGroup.add(rdScorrevole);
		rdScorrevole.setBounds(20, 143, 109, 23);
		contentPane.add(rdScorrevole);
		
		rdIntenso = new JRadioButton("Intenso");
		rdIntenso.setVisible(false);
		buttonGroup.add(rdIntenso);
		rdIntenso.setBounds(20, 169, 109, 23);
		contentPane.add(rdIntenso);
		
		btnInviaSegnalazione = new JButton("Invia segnalazione");
		btnInviaSegnalazione.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				boolean controllo = false;
				if(rdAssente.isSelected()) {
					try {
						controllo = Segnalazione.inviaSegnalazione('R', comboBox.getSelectedItem().toString());
					} 
					catch (MalformedURLException | RemoteException | NotBoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(rdIntenso.isSelected()) {
					try {
						controllo = Segnalazione.inviaSegnalazione('I', comboBox.getSelectedItem().toString());
					} 
					catch (MalformedURLException | RemoteException | NotBoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					try {
						controllo = Segnalazione.inviaSegnalazione('M', comboBox.getSelectedItem().toString());
					} 
					catch (MalformedURLException | RemoteException | NotBoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(controllo) {
					btnNuovaSegnalazione.setVisible(true);
					lblSelezionareIlTipo.setVisible(false);
					rdAssente.setVisible(false);
					rdIntenso.setVisible(false);
					rdScorrevole.setVisible(false);
					btnInviaSegnalazione.setVisible(false);
					comboBox.setVisible(false);
				}
			}
		});
		btnInviaSegnalazione.setVisible(false);
		btnInviaSegnalazione.setBounds(20, 199, 125, 23);
		contentPane.add(btnInviaSegnalazione);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Info Traffico:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(250, 11, 244, 239);
		contentPane.add(panel);
		panel.setLayout(null);

		TimerTask aggiornamentoNotifiche = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Segnalazione segnalazione = new Segnalazione();
					String testo;
					testo = segnalazione.riceviNotifica(segnalazione.getPosizione());
					textArea.setText(testo);
				} 
				catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
		Timer timer = new Timer();
		timer.schedule(aggiornamentoNotifiche, 10000, 10000);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setBounds(10, 23, 224, 205);
		panel.add(textArea);
		
		lblErroreDiConnessione = new JLabel("Errore di connessione!");
		lblErroreDiConnessione.setVisible(false);
		lblErroreDiConnessione.setForeground(Color.RED);
		lblErroreDiConnessione.setBounds(30, 233, 115, 14);
		contentPane.add(lblErroreDiConnessione);
		
		comboBox = new JComboBox<String>();
		comboBox.setVisible(false);
		comboBox.setBounds(10, 65, 230, 20);
		contentPane.add(comboBox);
		
		btnNuovaSegnalazione = new JButton("Crea nuova segnalazione");
		btnNuovaSegnalazione.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnNuovaSegnalazione.setVisible(false);
				lblSelezionareIlTipo.setVisible(true);
				rdAssente.setVisible(true);
				rdIntenso.setVisible(true);
				rdScorrevole.setVisible(true);
				btnInviaSegnalazione.setVisible(true);
				comboBox.setVisible(true);
				Segnalazione segnalazione = MainApp.segnalazione;
				Set<String> set = segnalazione.riceviStrade(segnalazione.getPosizione());
				Iterator<String> i = set.iterator();
				comboBox.removeAllItems();
				while(i.hasNext()) {
					comboBox.addItem(i.next().replaceAll("£", " "));
				}
			}
		});
		btnNuovaSegnalazione.setBounds(10, 80, 180, 60);
		contentPane.add(btnNuovaSegnalazione);
		
	}
}
