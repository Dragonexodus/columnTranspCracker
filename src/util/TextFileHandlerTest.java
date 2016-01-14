package util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TextFileHandlerTest {

	@Test
	public void testReadFile() {
		
		TextFileHandler fh = new TextFileHandler("ressource/testFile.txt");
		String text = fh.readText();
		assertEquals(text,"a digital computer is a machine that can solve problems for people by carrying out instructions given to itx. blaa");
		//fail("Not yet implemented");
	}
	
	@Test
	public void testWriteFile(){
		TextFileHandler fh = new TextFileHandler("ressource/testOutput.txt");
		String text = "Hallo Geheimnis!";
		fh.writeText(text);
		assertEquals(fh.readText(), text);
	}

}
