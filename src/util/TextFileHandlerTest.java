package util;

import static org.junit.Assert.*;

import java.io.File;

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
		
		File file = new File("ressource/testOutput.txt");
		if(file.exists()) file.delete();
		TextFileHandler fh = new TextFileHandler("ressource/testOutput.txt");
		String text = "Hallo Geheimnis!";
		fh.writeText(text);
		assertEquals(fh.readText(), text);
	}

}
