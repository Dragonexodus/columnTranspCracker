package util;

/**
 * Created by celentano on 19.01.16.
 */
public class Main {

    public static void main(String args[]) {
        TextFileHandler tfh = new TextFileHandler("ressource/testFileencode.txt");
        String str = tfh.readText().replaceAll((" "), "");
        Cracker cr = new Cracker(str, "computer");

        if (cr.crackByKnownWord())
            cr.testTransposition2();
//            if (cr.testTransposition())
//                System.out.println(cr.getCrackedSecret());
    }
}
