package gestoreAccessoDatabase;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class GestoreAccessoDatabaseTest {
	
	
	@Test
	void testModificaPassword() throws RemoteException {
		GestoreAccessoDatabase gestore = GestoreAccessoDatabase.getIstance();
		gestore.modificaPassword("fedemeg", "federico");
		String[] ris = gestore.getCredenziali("fedemeg");
		assertEquals("federico", ris[3]);
	}

	@Test
	void testGetCredenziali() throws RemoteException {
		GestoreAccessoDatabase gestore = GestoreAccessoDatabase.getIstance();
		assertArrayEquals(new String[] {"federico","megler","fedemeg","federico","1"}, gestore.getCredenziali("fedemeg"));
	}

	@Test
	void testOttieniMaxIDCentralina() throws RemoteException {
		GestoreAccessoDatabase gestore = GestoreAccessoDatabase.getIstance();
		assertEquals(84 , gestore.ottieniMaxIDCentralina());
	}

	@Test
	void testModificaDatiCentralina() throws RemoteException, SQLException {
		GestoreAccessoDatabase gestore = GestoreAccessoDatabase.getIstance();
		assertEquals(true, gestore.modificaDatiCentralina(5, 10, "3750£2000", "3750£3000"));
		ResultSet set = gestore.ottieniDatiCentralina(83);
		while(set.next()) {
			assertEquals(5, set.getInt("limite_auto"));
			assertEquals(10, set.getInt("limite_velocita"));
			assertEquals("via verdi", set.getString("nome_via"));
		}
	}

}
