package gestoreDatiStradali;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;

import org.junit.jupiter.api.Test;

class GestoreDatiStradaliTest {

	@Test
	void testComputaTraffico() throws RemoteException {
		GestoreDatiStradali gestore = GestoreDatiStradali.getIstance();
		assertEquals('I',gestore.computaTraffico(40, 20, 50, 70));
		assertEquals('M' ,gestore.computaTraffico(40, 35, 50, 70));
		assertEquals('R' ,gestore.computaTraffico(40, 42, 50, 70));
	}

}
