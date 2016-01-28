package util;

/**
 * Created by celentano on 19.01.16.
 */
public class Main {

    public static void main(String args[]) {
        
    	//showCoding();
    	showCracker();
    	
    }
    
    private static void showCoding(){
    	
    	Encoder enc = new Encoder("5,4,3,2,1,0","ressource/testFile.txt");
    	enc.encodeToFile();
    	Decoder dec = new Decoder("5,4,3,2,1,0","ressource/testFileEncoded.txt");
    	dec.decodeToFile();
    	
    }
    
    private static void showCracker(){
    	
    	TextFileHandler tfh = new TextFileHandler("ressource/testFile.txt");
        String str = tfh.readText().replaceAll((" "), "");
        Cracker cr = new Cracker(str, "comput");

        if (cr.crackByKnownWord())
            cr.testTransposition();
//            if (cr.testTransposition())
//                System.out.println(cr.getCrackedSecret());
    	
    }
    
}
