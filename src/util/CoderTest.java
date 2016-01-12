package util;

import static org.junit.Assert.*;

import org.junit.Test;

public class CoderTest {

	@Test
	public void testDoSth() {
		Coder test = new Coder(null, "ressource/testFile.txt");
		test.doSth("ressource/testFile.txt");
	}

}
