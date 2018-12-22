package graficaUtente;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import MainClient.Main;
import utente.Utente;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.beans.PropertyChangeEvent;
import javax.swing.JCheckBox;

public class LogIn extends JFrame {

	private JPanel contentPane;
	private JTextField txtNickname;
	private JPasswordField password;
	private JLabel lblErroreCred;
	private JButton btnLogIn;
	private JButton btnRegistrati;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn frame = new LogIn();
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
	
	public LogIn() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LogIn.class.getResource("/Resources/IconaSemaforo.png")));
		setMaximumSize(new Dimension(500, 350));
		setMinimumSize(new Dimension(450, 300));
		setTitle("Log In");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNickname = new JLabel("Nickname:");
		lblNickname.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNickname.setBounds(10, 43, 130, 14);
		contentPane.add(lblNickname);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(10, 68, 130, 14);
		contentPane.add(lblPassword);
		
		txtNickname = new JTextField();
		txtNickname.setToolTipText("");
		txtNickname.setHorizontalAlignment(SwingConstants.LEFT);
		txtNickname.setBounds(150, 40, 180, 20);
		contentPane.add(txtNickname);
		txtNickname.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(150, 65, 180, 20);
		contentPane.add(password);
		
		btnLogIn = new JButton("Log In");
		btnLogIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				boolean controllo;
				try {
					controllo = Utente.credenzialiValide(txtNickname.getText(),password.getText());
					if(!controllo) {
						lblErroreCred.setVisible(true);
					}
					else {
						String[] vettore = Utente.ottieniCredenziali(txtNickname.getText());
						Utente.setUtente(vettore[0], vettore[1], vettore[2], vettore[3], Integer.parseInt(vettore[4]));
						if(Integer.parseInt(vettore[4]) == 1) {
							PaginaUtente.main(true);
						}
						else {
							PaginaUtente.main(false);
						}
						dispose();
					}
				} catch (MalformedURLException | RemoteException | NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnLogIn.setBounds(190, 121, 89, 23);
		contentPane.add(btnLogIn);
		
		btnRegistrati = new JButton("Registrati");
		btnRegistrati.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//setVisible(false);
				SignUp.main(false);
				dispose();
			}
		});
		btnRegistrati.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnRegistrati.setBounds(190, 189, 89, 23);
		contentPane.add(btnRegistrati);
		
		JLabel lblNoAccount = new JLabel("Non hai un account?");
		lblNoAccount.setBounds(186, 164, 129, 14);
		contentPane.add(lblNoAccount);
		
		lblErroreCred = new JLabel("Credenziali errate!");
		lblErroreCred.setVisible(false);
		lblErroreCred.setForeground(Color.RED);
		lblErroreCred.setBounds(289, 125, 135, 14);
		contentPane.add(lblErroreCred);
		
		JCheckBox chckbxMostraPassword = new JCheckBox("Mostra password");
		chckbxMostraPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(chckbxMostraPassword.isSelected()) {
					password.setEchoChar((char)0);
				}
				else { 
					password.setEchoChar('•');		
				}
			}
		});
		chckbxMostraPassword.setBounds(150, 91, 135, 23);
		contentPane.add(chckbxMostraPassword);
		
	}
}
