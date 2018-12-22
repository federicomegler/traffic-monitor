package graficaUtente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JLayeredPane;
import javax.swing.JDesktopPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JToolBar;
import javax.swing.JTabbedPane;
import java.awt.ScrollPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;

import MainClient.Main;
import utente.Utente;

import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JFormattedTextField;
import java.awt.Scrollbar;
import javax.swing.JSlider;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.awt.Toolkit;

public class Profilo extends JFrame {

	private JPanel contentPane;
	private JPasswordField vecchia;
	private JPasswordField conferma;
	private JPasswordField nuova;
	private JLabel lblPasswordErrata;
	private JLabel lblErroreConnessione;
	private JLabel lblPasswordModificata;
	private JLabel lblLunghezzaPasswordErrata;
	private JButton btnEliminaProfilo;
	private JLabel lblProfiloNonEliminabile;

	/**
	 * Launch the application.
	 */
	public static void main(Utente utente) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Profilo frame = new Profilo(utente);
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
	public Profilo(Utente utente) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Profilo.class.getResource("/Resources/IconaSemaforo.png")));
		setResizable(false);
		setTitle("Profilo");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 500, 325);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 11, 100, 14);
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblCognome = new JLabel("Cognome:");
		lblCognome.setBounds(10, 36, 100, 14);
		lblCognome.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblNickname = new JLabel("Nickname:");
		lblNickname.setBounds(10, 61, 100, 14);
		lblNickname.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblTipoAccount = new JLabel("Tipo account:");
		lblTipoAccount.setBounds(10, 86, 100, 14);
		lblTipoAccount.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblName = new JLabel(utente.getCredenziali().getNome());
		lblName.setBounds(120, 11, 100, 14);
		
		JLabel cognome = new JLabel(utente.getCredenziali().getCognome());
		cognome.setBounds(120, 36, 100, 14);
		
		JLabel nickname = new JLabel(utente.getCredenziali().getNickname());
		nickname.setBounds(120, 61, 100, 14);
		
		JLabel tipo_account = new JLabel();
		if(utente.getCredenziali().getIsAdmin() == 1) {
			tipo_account.setText("admin");
		}
		else {
			tipo_account.setText("utente");
		}
		tipo_account.setBounds(120, 86, 100, 14);
		contentPane.setLayout(null);
		contentPane.add(lblNome);
		contentPane.add(lblCognome);
		contentPane.add(lblNickname);
		contentPane.add(lblTipoAccount);
		contentPane.add(lblName);
		contentPane.add(cognome);
		contentPane.add(nickname);
		contentPane.add(tipo_account);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Modifica Password:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 111, 270, 164);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblVecchiaPassword = new JLabel("Vecchia password:");
		lblVecchiaPassword.setBounds(10, 36, 115, 14);
		panel.add(lblVecchiaPassword);
		
		JLabel lblNuovaPassword = new JLabel("Nuova password:");
		lblNuovaPassword.setBounds(10, 61, 115, 14);
		panel.add(lblNuovaPassword);
		
		JLabel lblReinserirePassword = new JLabel("Reinserire password:");
		lblReinserirePassword.setBounds(10, 86, 115, 14);
		panel.add(lblReinserirePassword);
		
		JButton btnSalvaModifiche = new JButton("Salva modifiche");
		btnSalvaModifiche.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(vecchia.getText().equals(Utente.getUtente().getCredenziali().getPassword())) {
					if(nuova.getText().length() <= 16 && nuova.getText().length() >= 8) {
						if(nuova.getText().equals(conferma.getText())) {
							try {
								Utente.getUtente().modificaPassword(Utente.getUtente().getCredenziali().getNickname(), nuova.getText());
								Utente.getUtente().getCredenziali().setPassword(nuova.getText());
								lblErroreConnessione.setVisible(false);
								lblLunghezzaPasswordErrata.setVisible(false);
								lblPasswordErrata.setVisible(false);
								lblProfiloNonEliminabile.setVisible(false);
								lblPasswordModificata.setVisible(true);
							}
							catch (MalformedURLException | RemoteException | NotBoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								lblErroreConnessione.setVisible(true);
								lblLunghezzaPasswordErrata.setVisible(false);
								lblPasswordErrata.setVisible(false);
								lblProfiloNonEliminabile.setVisible(false);
								lblPasswordModificata.setVisible(false);
							}
						}
						else {
							lblErroreConnessione.setVisible(false);
							lblLunghezzaPasswordErrata.setVisible(false);
							lblPasswordErrata.setVisible(true);
							lblProfiloNonEliminabile.setVisible(false);
							lblPasswordModificata.setVisible(false);
						}
					}
					else {
						lblErroreConnessione.setVisible(false);
						lblLunghezzaPasswordErrata.setVisible(true);
						lblPasswordErrata.setVisible(false);
						lblProfiloNonEliminabile.setVisible(false);
						lblPasswordModificata.setVisible(false);
					}
				}
				else {
					lblErroreConnessione.setVisible(false);
					lblLunghezzaPasswordErrata.setVisible(false);
					lblPasswordErrata.setVisible(true);
					lblProfiloNonEliminabile.setVisible(false);
					lblPasswordModificata.setVisible(false);
				}
			}
		});
		btnSalvaModifiche.setBounds(10, 128, 115, 23);
		panel.add(btnSalvaModifiche);
		
		vecchia = new JPasswordField();
		vecchia.setBounds(135, 36, 125, 20);
		panel.add(vecchia);
		
		conferma = new JPasswordField();
		conferma.setBounds(135, 83, 125, 20);
		panel.add(conferma);
		
		nuova = new JPasswordField();
		nuova.setBounds(135, 60, 125, 20);
		panel.add(nuova);
		
		JCheckBox chckbxMostraPassword = new JCheckBox("Mostra password");
		chckbxMostraPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(chckbxMostraPassword.isSelected()) {
					nuova.setEchoChar((char)0);
					vecchia.setEchoChar((char)0);
					conferma.setEchoChar((char)0);
				}
				else { 
					nuova.setEchoChar('•');
					vecchia.setEchoChar('•');
					conferma.setEchoChar('•');
				}
			}
		});
		chckbxMostraPassword.setBounds(131, 110, 129, 23);
		panel.add(chckbxMostraPassword);
		
		lblErroreConnessione = new JLabel("Errore connessione!");
		lblErroreConnessione.setVisible(false);
		lblErroreConnessione.setForeground(Color.RED);
		lblErroreConnessione.setHorizontalAlignment(SwingConstants.CENTER);
		lblErroreConnessione.setBounds(135, 137, 125, 14);
		panel.add(lblErroreConnessione);
		
		lblPasswordModificata = new JLabel("Password modificata!");
		lblPasswordModificata.setVisible(false);
		
		lblPasswordErrata = new JLabel("Password errata!");
		lblPasswordErrata.setVisible(false);
		lblPasswordErrata.setForeground(Color.RED);
		lblPasswordErrata.setHorizontalAlignment(SwingConstants.CENTER);
		lblPasswordErrata.setBounds(135, 137, 125, 14);
		panel.add(lblPasswordErrata);
		lblPasswordModificata.setHorizontalAlignment(SwingConstants.CENTER);
		lblPasswordModificata.setForeground(new Color(50, 205, 50));
		lblPasswordModificata.setBounds(135, 137, 125, 14);
		panel.add(lblPasswordModificata);
		
		lblLunghezzaPasswordErrata = new JLabel("Lunghezza Password Err. ");
		lblLunghezzaPasswordErrata.setBounds(126, 137, 151, 14);
		panel.add(lblLunghezzaPasswordErrata);
		lblLunghezzaPasswordErrata.setVisible(false);
		lblLunghezzaPasswordErrata.setForeground(Color.RED);
		
		JButton btnHomepage = new JButton("Homepage");
		btnHomepage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(utente.getCredenziali().getIsAdmin() == 1) {
					PaginaUtente.main(true);
				}
				else {
					PaginaUtente.main(false);
				}
				dispose();
			}
		});
		btnHomepage.setBounds(364, 11, 120, 23);
		contentPane.add(btnHomepage);
		
		btnEliminaProfilo = new JButton("Elimina profilo");
		btnEliminaProfilo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Utente utente = Utente.getUtente();
				if(utente.getCredenziali().getIsAdmin() == 1) {
					lblProfiloNonEliminabile.setVisible(true);
				}
				else {
					try {
						Utente.eliminaUtente(utente.getCredenziali().getNickname());
						LogIn.main(null);
						dispose();
					} 
					catch (MalformedURLException | RemoteException | NotBoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		btnEliminaProfilo.setBounds(364, 252, 120, 23);
		contentPane.add(btnEliminaProfilo);
		
		lblProfiloNonEliminabile = new JLabel("Profilo non eliminabile!");
		lblProfiloNonEliminabile.setVisible(false);
		lblProfiloNonEliminabile.setForeground(Color.RED);
		lblProfiloNonEliminabile.setBounds(349, 227, 135, 14);
		contentPane.add(lblProfiloNonEliminabile);
	}
}
