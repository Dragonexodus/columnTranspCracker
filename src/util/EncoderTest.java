package util;

import static org.junit.Assert.*;

import org.junit.Test;

public class EncoderTest {

    @Test
    public void testSuccess() {
        Encoder test = new Encoder("0,1,2,3,4,5", "ressource/testFile.txt");
        assertEquals(0, test.encodeFile());
    }

    @Test
    public void testSuccessWithoutTranspositions() {
        Encoder test = new Encoder("", "ressource/testFile.txt");
        assertEquals(0, test.encodeFile());
    }

    @Test
    public void testFailsWithoutFilename() {
        Encoder test = new Encoder("0,1,2,3,4,5", "");
        assertEquals(-1, test.encodeFile());
    }

    @Test
    public void testFailsWithEmptyStrings() {
        Encoder test = new Encoder("", "");
        assertEquals(-1, test.encodeFile());
    }

    @Test
    public void testFailsWithNull() {
        Encoder test = new Encoder(null, null);
        assertEquals(-1, test.encodeFile());
    }
}
