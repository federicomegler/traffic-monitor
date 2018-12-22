package graficaUtente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import MainClient.Main;
import grafico.RiempiGiorno;
import utente.Utente;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.awt.Toolkit;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.JLayeredPane;
import javax.swing.JTabbedPane;
import java.awt.Canvas;
import java.awt.Rectangle;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import java.awt.Choice;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.EtchedBorder;

public class PaginaUtente extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2374078284307603564L;
	private JPanel contentPane;
	private JMenuItem mntmChiudi;
	private JComboBox comboBox;
	private static JInternalFrame internalMappa;
	private JComboBox comboBox_1;
	
	public static JInternalFrame getInternalMappa() {
		return internalMappa;
	}

	/**
	 * Launch the application.
	 */
	public static void main(boolean type) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PaginaUtente frame = new PaginaUtente(type);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws NotBoundException  
	 * @throws PropertyVetoException 
	 * @throws SQLException 
	 */
	public PaginaUtente(boolean type) throws NotBoundException, IOException, PropertyVetoException, SQLException {
		setMinimumSize(new Dimension(800, 650));
		setTitle("Traffic Monitor");
		setIconImage(Toolkit.getDefaultToolkit().getImage(PaginaUtente.class.getResource("/Resources/IconaSemaforo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(25, 25, 650, 450);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFunzioni = new JMenu("Funzioni");
		menuBar.add(mnFunzioni);
		
		mntmChiudi = new JMenuItem("Chiudi");
		mntmChiudi.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				dispose();
			}
		});
		
		JMenu mnGestisciCentraline = new JMenu("Gestisci Centraline");
		mnGestisciCentraline.setVisible(type);
		mnFunzioni.add(mnGestisciCentraline);
		
		JMenuItem mntmCrea = new JMenuItem("Modifica centralina");
		mntmCrea.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ImpostazioniCentralina impostazioni = new ImpostazioniCentralina();
				impostazioni.setVisible(true);
				dispose();
			}
		});
		
		mnGestisciCentraline.add(mntmCrea);
		mnFunzioni.add(mntmChiudi);
		
		JMenu mnAmministratore = new JMenu("Amministratore");
		mnAmministratore.setVisible(type);
		menuBar.add(mnAmministratore);
		
		JMenuItem mntmNuovoAdmin = new JMenuItem("Nuovo admin");
		mntmNuovoAdmin.setEnabled(type);
		mntmNuovoAdmin.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				SignUp.main(true);
				dispose();
			}
		});
		mnAmministratore.add(mntmNuovoAdmin);
		
		JMenu mnProfilo = new JMenu("Profilo");
		menuBar.add(mnProfilo);
		
		JMenuItem mntmVisualizzaProfilo = new JMenuItem("Visualizza profilo");
		mntmVisualizzaProfilo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Profilo.main(Utente.getUtente());
				dispose();
			}
		});
		mnProfilo.add(mntmVisualizzaProfilo);		
		
		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				LogIn.main(null);
				dispose();
			}
		});
		mnProfilo.add(mntmLogOut);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblSelezionareVia = new JLabel("Selezionare via:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblSelezionareVia, 11, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblSelezionareVia, 400, SpringLayout.WEST, contentPane);
		contentPane.add(lblSelezionareVia);
		
		Mappa mappa = Mappa.getIstance();
		mappa.setLinee(Mappa.getIstance().getLineeServer());
		internalMappa = new JInternalFrame("Mappa");
		internalMappa.setBorder(null);
		sl_contentPane.putConstraint(SpringLayout.NORTH, internalMappa, 25, SpringLayout.SOUTH, lblSelezionareVia);
		sl_contentPane.putConstraint(SpringLayout.WEST, internalMappa, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, internalMappa, -24, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, internalMappa, -274, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblSelezionareVia, 0, SpringLayout.EAST, internalMappa);
		contentPane.add(internalMappa);
		internalMappa.setIcon(true);
		internalMappa.setFrameIcon(new ImageIcon(PaginaUtente.class.getResource("/Resources/Wooden-Map.png")));
		internalMappa.getContentPane().add(mappa);
		mappa.setLayout(null);
		
		JButton button = new JButton("");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					mappa.setLinee(mappa.getLineeServer());
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				internalMappa.setVisible(false);
				internalMappa.setVisible(true);
			}
		});
		button.setIcon(new ImageIcon(PaginaUtente.class.getResource("/Resources/r.png")));
		button.setBounds(456, 11, 24, 23);
		mappa.add(button);
		
		JLabel lblNewLabel = new JLabel("Refresh!");
		lblNewLabel.setBounds(404, 13, 46, 14);
		mappa.add(lblNewLabel);
		
		comboBox = new JComboBox();
		sl_contentPane.putConstraint(SpringLayout.NORTH, comboBox, -3, SpringLayout.NORTH, lblSelezionareVia);
		sl_contentPane.putConstraint(SpringLayout.WEST, comboBox, 6, SpringLayout.EAST, lblSelezionareVia);
		sl_contentPane.putConstraint(SpringLayout.EAST, comboBox, -120, SpringLayout.EAST, contentPane);
		comboBox.setAutoscrolls(true);
		comboBox.setEditable(true);
		Set<String> vettore = mappa.ottieniVie();
		Iterator<String> i = vettore.iterator();
		while(i.hasNext()) {
			comboBox.addItem(i.next());
		}
		contentPane.add(comboBox);
		JButton btnStatistiche = new JButton("Statistiche");
		btnStatistiche.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
				String[] s = comboBox_1.getSelectedItem().toString().split("\\ ");
					try {
						RiempiGiorno.riempiGrafico(s[0]+" "+s[1], s[2]+"£"+s[3], s[4]+"£"+s[5]);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			
			}
		});
		JButton btnCerca = new JButton("Cerca");
		comboBox_1 = new JComboBox();
		btnCerca.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				mappa.setVia((String)comboBox.getSelectedItem());
				internalMappa.setVisible(false);
				internalMappa.setVisible(true);
				if(Utente.getUtente().getCredenziali().getIsAdmin() == 1) {
				btnStatistiche.setVisible(true);
				comboBox_1.removeAllItems();
				Line linea;
				Iterator<Line> j = mappa.getLinee().iterator();

				while(j.hasNext()) {
					linea = j.next();
					if(linea.isPresenza() && comboBox.getSelectedItem().equals(linea.getNome())) 				
						comboBox_1.addItem(linea.getNome() + " " + linea.getY1() + " " + linea.getX1() +" " + linea.getY2() + " "  + linea.getX2());
					
				}

				comboBox_1.setVisible(true);
				}
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCerca, 6, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnCerca, -29, SpringLayout.EAST, contentPane);
		contentPane.add(btnCerca);
		
		
		comboBox_1.setVisible(false);
		sl_contentPane.putConstraint(SpringLayout.NORTH, comboBox_1, 37, SpringLayout.SOUTH, btnCerca);
		sl_contentPane.putConstraint(SpringLayout.WEST, comboBox_1, -157, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, comboBox_1, -5, SpringLayout.EAST, contentPane);
		contentPane.add(comboBox_1);
		
		
		btnStatistiche.setVisible(false);
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnStatistiche, 36, SpringLayout.SOUTH, comboBox_1);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnStatistiche, 0, SpringLayout.EAST, comboBox_1);
		contentPane.add(btnStatistiche);
		internalMappa.setVisible(true);
		
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
