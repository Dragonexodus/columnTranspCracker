package util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TranspositionTest {

	@Test
	public void testConstructorZero() {
		
		Transposition t = new Transposition(0);
		assertEquals(t.getTransposition().isEmpty(), true);
		
	}

}
