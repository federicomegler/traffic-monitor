package gestoreAccessoSistema;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;

import org.junit.jupiter.api.Test;

class GestoreAccessoSistemaTest {

	@Test
	void testEsisteUtente() throws RemoteException {
		GestoreAccessoSistema gestore = GestoreAccessoSistema.getIstance();
		assertEquals(1,gestore.esisteUtente("fedemeg"));
		assertEquals(0, gestore.esisteUtente("nonesiste"));
	}

	@Test
	void testCredValide() throws RemoteException {
		GestoreAccessoSistema gestore = GestoreAccessoSistema.getIstance();
		assertEquals(true,gestore.credValide("fedemeg", "federico"));
		assertEquals(false,gestore.credValide("fedemeg", "sbagliata"));
	}

}
