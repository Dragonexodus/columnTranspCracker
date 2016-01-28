package util;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class DecoderTest {

	@Test
	public void testDecoded() {
		
		TextFileHandler in;
		String expected;
		
		in  = new TextFileHandler("ressource/testOutput.txt");
		expected = in.readText().replaceAll("[^0-9a-zA-Z]", "");
		
		if(!new File("ressource/testOutputEncoded.txt").exists()){
			
			Encoder enc = new Encoder("5,4,3,2,1,0","ressource/testOutput.txt");
			enc.encodeFile();
		}
		
		
		Decoder dec = new Decoder("5,4,3,2,1,0","ressource/testOutputEncoded.txt");
		dec.decode();
		in = new TextFileHandler("ressource/testOutputEncodedDecoded.txt");
		String actual = in.readText();
		
		assertEquals(true, actual.contains(expected));
		
		new File("ressource/testOutputEncoded.txt").delete();
		new File("ressource/testOutputEncodedDecoded.txt").delete();
		
	}

}