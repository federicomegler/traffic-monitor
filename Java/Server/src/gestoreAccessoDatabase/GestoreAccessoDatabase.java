package gestoreAccessoDatabase;
import java.lang.Thread.State;
import java.rmi.RemoteException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import gestoreDirettive.GestoreDirettive;

public class GestoreAccessoDatabase {
	
	private static GestoreAccessoDatabase instance = null;
	private final String URL = "jdbc:mysql://localhost/utente?useUnicode=true&useJDBCCompliant"
			                   + "TimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private final String USER = "root";
	private final String PASS = "gruppo6";
	private Connection conn;
	
	public static GestoreAccessoDatabase getIstance() throws RemoteException{
		if(instance == null) {
			instance = new GestoreAccessoDatabase();
		}
		return instance;
	}
	
	
	protected GestoreAccessoDatabase() {
		try {
			conn =  DriverManager.getConnection(URL,USER,PASS);
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public boolean modificaPassword(String nickname, String password) {
		try {
			Statement statement = conn.createStatement();
			String query = new String("UPDATE utente SET password = '" + password + "' WHERE nickname = '" + nickname + "';");
			statement.executeUpdate(query);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	//metodi di lettura nel database
	
	public ResultSet ottieniDatiStats(String inizio, String fine) {
		try {
			inizio = inizio.replaceAll(" ", "£");
			fine = fine.replaceAll(" ", "£");
			Statement state = conn.createStatement();
			String query = new String("select * from datostradale where inizio = '" + inizio + "' and fine = '" 
			                          + fine + "'");
			ResultSet ris = state.executeQuery(query);
			return ris;
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public ResultSet ottieniVie() throws SQLException {
		Statement statement = conn.createStatement();
		String query = new String("select * from strada");
		ResultSet result = statement.executeQuery(query);
		return result;
	}
	
	public String[] getCredenziali(String nickname) {
		String[] vettore = null;
		try {
			
			Statement statement = conn.createStatement();
			
			ResultSet res = statement.executeQuery("select * from utente where nickname = '" + nickname + "'");				
			
			while(res.next()) {
				vettore = new String[5];
				vettore[0] = res.getString("nome");
				vettore[1] = res.getString("cognome");
				vettore[2] = res.getString("nickname");
				vettore[3] = res.getString("password");
				vettore[4] = Integer.toString(res.getInt("admin"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return vettore;
	}
	
	public void eliminaDatiStradali() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {	
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_MONTH, -7);
					DateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Statement state = conn.createStatement();
					String query = new String("delete from datostradale where data < '" + date.format(cal.getTime()) + "'");
					state.executeUpdate(query);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		timer.schedule(task, 0 , 3600);
	}
	
	
	public ResultSet ottieniDatiCentralina(int id_centralina) {
		
		try {
			Statement state = conn.createStatement();
			String query = new String("select * from centralina where idcentralina = " + Integer.toString(id_centralina));
			ResultSet risultato = state.executeQuery(query);
			return risultato;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public int ottieniMaxIDCentralina() {
		int max = 0;
		try {
			Statement state = conn.createStatement();
			String query = new String("select max(idcentralina) as massimo from centralina");
			ResultSet ris = state.executeQuery(query);
			while(ris.next()) {
				max = ris.getInt("massimo") + 1;
			}
			return max;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public ResultSet ottieniCentraline() {
		try {
			Statement state = conn.createStatement();
			String query = new String("select * from centralina");
			ResultSet risultato = state.executeQuery(query);
			return risultato;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	//metodi di scrittura nel database
	public void salvaDatiCentralina(int id_centralina, String inizio, String fine, int velocita_media, int numero_auto, String data, char tipo) throws SQLException {
		Statement statement = conn.createStatement();
		String sql = new String("insert into datostradale values"
				                + " (" + id_centralina + "  , '" + inizio + "','" + fine + "'," + velocita_media + "," 
				                + numero_auto + ",'" + data + "','" + tipo + "')");
		statement.executeUpdate(sql);		
	}
	
	public boolean modificaDatiCentralina(int n_limite_auto, int limite_vel, String inizio, String fine) {
		try {
			Statement state = conn.createStatement();
			
			String query = new String("update centralina set limite_velocita = " + Integer.toString(limite_vel) + ", limite_auto = "
					+ Integer.toString(n_limite_auto) + " where inizio = '" + inizio + "' and fine = '" + fine + "'");
			
			state.executeUpdate(query);
			return true;
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public void setCredenziali(String nome, String cognome, String nickname, String password, int isAdmin) {
		try {			
			Statement statement = conn.createStatement();
			String sql = new String("insert into utente values"
					+ " ('" + nome + " ' , ' " + cognome + "','" + nickname + "','" + password + "'," + Integer.toString(isAdmin) + ")");
			
			statement.executeUpdate(sql);
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void salvaNuovaCentralina(int idcentralina, String inizio, String fine, int limite_vel, int limite_auto, String nome_via) {
		try {
			Statement state = conn.createStatement();
			String query = new String("insert into centralina values " +
			                          "('" + idcentralina + "','" + inizio + "','" + fine +"'," +limite_vel + "," +
					                     limite_auto +",'" + nome_via + "')");
			state.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void aggiornaStrada(int idcentralina, String inizio, String fine) {
		try {
			Statement state = conn.createStatement();
			String query = new String("update strada set idcentralina = " + Integer.toString(idcentralina) + " where inizio = '" +
					                    inizio + "' and fine = '" + fine + "'");
			state.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//controllo se un nickname esiste già
	public int controlloNick(String nickname) {
		//boolean ritorno = false;
		
		try {
			Statement statement = conn.createStatement();
			String query = new String("select nickname from utente where nickname = '" + nickname +"'");
			ResultSet risultato = statement.executeQuery(query);
			
			
			if(risultato.next()) {
				
				return 0;
			}
			else { 
				
				return 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	public boolean eliminaUtente(String nickname) {
		try {
			Statement state = conn.createStatement();
			String query = new String("delete from utente where nickname='"+nickname+"'");
			state.executeUpdate(query);
			return true;
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public void eliminaCentralina(int idcentralina) {
		
		
		try {
			Statement state = conn.createStatement();
			String query = new String("delete from centralina where idcentralina="+idcentralina);
			state.executeUpdate(query);
		    query = ("delete from datostradale where idcentralina="+idcentralina);
			state.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
