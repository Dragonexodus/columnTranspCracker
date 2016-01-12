package util;

import static org.junit.Assert.*;

import org.junit.Test;

public class CoderTest {

	@Test
	public void testencodeFile() {
		Coder test = new Coder(null, "ressource/testFile.txt");
		test.encodeFile();
	}

}
