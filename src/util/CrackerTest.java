package util;

import org.junit.Test;

/**
 * Created by celentano on 19.01.16.
 */
public class CrackerTest {

    TextFileHandler tfh = new TextFileHandler("ressource/testFileencode.txt");
    String str = tfh.readText().replaceAll((" "), "");
    Cracker cr = new Cracker(str, "computer");

    @Test
    public void testIsCracked() throws Exception {

    }

    @Test
    public void testGetCrackedSecret() throws Exception {

    }

    @Test
    public void testCrackByKnownWord() throws Exception {
        cr.crackByKnownWord();
    }
}