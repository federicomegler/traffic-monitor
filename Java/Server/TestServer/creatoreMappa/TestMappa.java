package creatoreMappa;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestMappa {

	@Test
	void testRiferimento() {
		Mappa mappa = Mappa.getIstance();
		assertEquals("1500£500", mappa.riferimento("1500£600"));
	}

}
