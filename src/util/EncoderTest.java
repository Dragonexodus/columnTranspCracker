package util;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class EncoderTest {

    @Test
    public void testSuccess() {
        Encoder test = new Encoder("0,1,2,3,4,5", "ressource/testFile.txt");
        assertEquals(0, test.encodeToFile());
        new File("ressource/testFileEncoded.txt").delete();
    }

    @Test
    public void testSuccessWithoutTranspositions() {
        Encoder test = new Encoder("", "ressource/testFile.txt");
        assertEquals(0, test.encodeToFile());
        new File("ressource/testFileEncoded.txt").delete();
    }

    @Test
    public void testFailsWithoutFilename() {
        Encoder test = new Encoder("0,1,2,3,4,5", "");
        assertEquals(-1, test.encodeToFile());
    }

    @Test
    public void testFailsWithEmptyStrings() {
        Encoder test = new Encoder("", "");
        assertEquals(-1, test.encodeToFile());
    }

    @Test
    public void testFailsWithNull() {
        Encoder test = new Encoder(null, null);
        assertEquals(-1, test.encodeToFile());
    }
    
    @Test
    public void testGenerateFiles(){
    	
    	Encoder enc = new Encoder("0,1,2,3,4,5","ressource/testOutput.txt");
    	enc.encodeToFile();
    	assertEquals(true,new File("ressource/testOutputEncoded.txt").exists());
    	new File("ressource/testFileEncoded.txt").delete();
    }
    
}
