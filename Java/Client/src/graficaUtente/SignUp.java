package graficaUtente;

import graficaUtente.LogIn;
import utente.Utente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import MainClient.Main;

import javax.swing.JLabel;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.awt.Dimension;
import javax.swing.JProgressBar;

public class SignUp extends JFrame {

	private JPanel contentPane;
	private JTextField textNome;
	private JTextField textCognome;
	private JTextField textNick;
	private JPasswordField pwdNuova;
	private JPasswordField pwdConferma;
	private JLabel lblPasswordErrata;
	private JLabel lblNicknameGiEsistente;
	private JCheckBox chckbxMostraPassword;
	private JLabel lblMinCaratteri;
	private JLabel lblErroreDiConnessione;
	private JButton btnLogIn;

	/**
	 * Launch the application.
	 */
	public static void main(boolean type) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp(type);
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
	public SignUp(boolean type) {
		setResizable(false);
		setMinimumSize(new Dimension(500, 300));
		setIconImage(Toolkit.getDefaultToolkit().getImage(SignUp.class.getResource("/Resources/IconaSemaforo.png")));
		if(type) {
			setTitle("Sign Up (Admin)");
		}
		else {
			setTitle("Sign Up");	
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setBounds(10, 40, 130, 14);
		contentPane.add(lblNome);
		
		JLabel lblCognome = new JLabel("Cognome:");
		lblCognome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCognome.setBounds(10, 65, 130, 14);
		contentPane.add(lblCognome);
		
		JLabel lblNickname = new JLabel("Nickname:");
		lblNickname.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNickname.setBounds(10, 93, 130, 14);
		contentPane.add(lblNickname);
		
		JLabel lblPassword = new JLabel("Password (8-16):");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(10, 118, 130, 14);
		contentPane.add(lblPassword);
		
		JLabel lblConfermaPassword = new JLabel("Conferma password:");
		lblConfermaPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConfermaPassword.setBounds(10, 143, 130, 14);
		contentPane.add(lblConfermaPassword);
		
		textNome = new JTextField();
		textNome.setBounds(150, 37, 170, 20);
		contentPane.add(textNome);
		textNome.setColumns(10);
		
		textCognome = new JTextField();
		textCognome.setBounds(150, 62, 170, 20);
		contentPane.add(textCognome);
		textCognome.setColumns(10);
		
		textNick = new JTextField();
		textNick.setBounds(150, 90, 170, 20);
		contentPane.add(textNick);
		textNick.setColumns(10);
		
		pwdNuova = new JPasswordField();
		pwdNuova.setBounds(150, 115, 170, 20);
		contentPane.add(pwdNuova);
		
		pwdConferma = new JPasswordField();
		pwdConferma.setBounds(150, 140, 170, 20);
		contentPane.add(pwdConferma);
		
		JButton btnRegistrati = new JButton("Sign Up");
		btnRegistrati.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int controllo = 1;
				/*controllo che le due password siano uguali, le psw rispettino le lunghezze, poi mando al server i dati*/
				if((pwdNuova.getText().length() < 8) || (pwdNuova.getText().length() > 16)) {
					lblPasswordErrata.setVisible(false);
					lblMinCaratteri.setVisible(true);
					controllo = 0;
				}
				else {
					lblMinCaratteri.setVisible(false);
					lblPasswordErrata.setVisible(false);
					
					//controllo che le password corrispondano
					if(!pwdNuova.getText().equals(pwdConferma.getText())) {
						lblPasswordErrata.setVisible(true);
						controllo = 0;
					}
					else {
						lblPasswordErrata.setVisible(false);
					}
				}
				
				if(controllo == 1) {
					try {
						if(type) {
							controllo = Utente.creaUtente(textNome.getText(), textCognome.getText(), textNick.getText(), pwdNuova.getText(), 1);
						}
						else {
							controllo = Utente.creaUtente(textNome.getText(), textCognome.getText(), textNick.getText(), pwdNuova.getText(), 0);
						}
						
						
						if(controllo == -1) {
							lblNicknameGiEsistente.setVisible(false);
							lblErroreDiConnessione.setVisible(true);
						}
						else if(controllo == 0) {
							lblErroreDiConnessione.setVisible(false);
							lblNicknameGiEsistente.setVisible(true);
						}
						else {
							if(type) {
								PaginaUtente.main(type);
								dispose();
							}
							else {
								Main.main(null);
								dispose();
							}
						}
					} catch (MalformedURLException | RemoteException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnRegistrati.setBounds(190, 193, 89, 23);
		contentPane.add(btnRegistrati);
		
		lblNicknameGiEsistente = new JLabel("Nickname gi\u00E0 esistente");
		lblNicknameGiEsistente.setVisible(false);
		lblNicknameGiEsistente.setForeground(Color.RED);
		lblNicknameGiEsistente.setBounds(350, 93, 129, 14);
		contentPane.add(lblNicknameGiEsistente);
		
		lblMinCaratteri = new JLabel("Lunghezza invalida");
		lblMinCaratteri.setVisible(false);
		lblMinCaratteri.setForeground(Color.RED);
		lblMinCaratteri.setBounds(350, 118, 129, 14);
		contentPane.add(lblMinCaratteri);
		
		lblPasswordErrata = new JLabel("Non corrispondono!");
		lblPasswordErrata.setVisible(false);
		lblPasswordErrata.setForeground(Color.RED);
		lblPasswordErrata.setBounds(350, 143, 144, 20);
		contentPane.add(lblPasswordErrata);
		
		chckbxMostraPassword = new JCheckBox("Mostra password");
		chckbxMostraPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(chckbxMostraPassword.isSelected()) {
					pwdNuova.setEchoChar((char)0);
					pwdConferma.setEchoChar((char)0);
				}
				else { 
					pwdConferma.setEchoChar('•');
					pwdNuova.setEchoChar('•');
				}
			}
		});
		chckbxMostraPassword.setBounds(150, 163, 170, 23);
		contentPane.add(chckbxMostraPassword);
		
		lblErroreDiConnessione = new JLabel("Errore di connessione!");
		lblErroreDiConnessione.setVisible(false);
		lblErroreDiConnessione.setForeground(Color.RED);
		lblErroreDiConnessione.setBounds(190, 227, 130, 14);
		contentPane.add(lblErroreDiConnessione);
		
		btnLogIn = new JButton("Log In");
		btnLogIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				LogIn.main(null);
				dispose();
			}
		});
		btnLogIn.setBounds(390, 11, 89, 23);
		contentPane.add(btnLogIn);
	}
}
