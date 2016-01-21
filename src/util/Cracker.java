package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cracker {

    private BlockMatrix bm;
    private boolean cracked;
    private final String SECRET;
    private String plain;

    String knownWord;
    List<Integer> folge;
    int firstMatchRow;
    int lineBreak = 0;

    public Cracker(String secret, String knownWord) {
        this.SECRET = secret;
        this.cracked = false;
        this.knownWord = knownWord;
        this.plain = "";
    }

    public boolean isCracked() {
        return cracked;
    }

    public String getCrackedSecret() {

        for (int i = 0; i < bm.getLineLength(); i++)
            for (int m = 0; m < bm.getBlockLength(); m++)
                plain += bm.getArray()[i][m];

        return this.plain;
    }

    public boolean testTransposition() {

        int foundCount = 0;
        int l = 0;

        for (int k = 0; k < bm.getBlockLength() + 1; k++) {

            Collections.rotate(folge, 1);
            String str = folge.toString();
            str = str.replaceAll(("[,\\[\\] ]"), "");
            System.out.println(str);

            Transposition tr = new Transposition(str);

            BlockMatrix test = new BlockMatrix(SECRET.toCharArray(), tr, false);
            test.transpose();

            //TODO: Testausgabe
            if (true) {
                System.out.println(SECRET);
                for (int i = 0; i < test.getLineLength(); i++) {
                    for (int j = 0; j < test.getBlockLength(); j++)
                        System.out.print(test.getArray()[i][j] + " ");
                    System.out.println("");
                }
                System.out.println("");
            }

            for (int i = firstMatchRow; i < test.getLineLength(); i++) {
                for (int m = 0; m < test.getBlockLength(); m++) {

                    if (foundCount >= knownWord.length()) {
                        System.out.print("-> TR GEFUNDEN: ");
                        System.out.println(folge);

                        bm = test;
                        return true;
                    }
                    if (test.getArray()[i][m] == knownWord.charAt(l)) {
                        l++;
                        foundCount++;
                    } else {
                        l = 0;
                        foundCount = 0;
                    }
                }
            }
        }
        return false;
    }

    public boolean crackByKnownWord() {

        boolean found = false;

        // Fall 1: Wir testen eine Block-Länge von 2 bis knownWord.length() --------------------------------------------
        for (int k = 2; k < knownWord.length(); k++) {

            if (SECRET.length() % k != 0) // hier werden nur passende Blockgrößen durchgelassen
                continue;

            //TODO: Tetst
            Transposition t;
            if (k == 6)
                t = new Transposition("215043");
//                t = new Transposition("012345");
            else
                t = new Transposition(k);

            // Diese dient zum Abschätzen der Transposition
            Transposition guessed = new Transposition(0);

            this.bm = new BlockMatrix(this.SECRET.toCharArray(), t, false);
            bm.transpose(); //TODO: Test
            BlockMatrix known = new BlockMatrix(knownWord.toCharArray(), t, true);

            //TODO: Test
            if (true)
                if (k == 6) {
                    System.out.println(SECRET);
                    for (int i = 0; i < bm.getLineLength(); i++) {
                        for (int j = 0; j < k; j++)
                            System.out.print(bm.getArray()[i][j] + " ");
                        System.out.println("");
                    }
                    System.out.println("");
                    for (int i = 0; i < known.getLineLength(); i++) {
                        for (int j = 0; j < k; j++)
                            if (known.getArray()[i][j] == '\0')
                                System.out.print("_ ");
                            else
                                System.out.print(known.getArray()[i][j] + " ");
                        System.out.println("");
                    }
                    System.out.println("");
                }

            // Init -----------------------------------------------------------
            folge = new ArrayList<Integer>();
            int blockSize = k;
            int foundCount = 0;                 // Anzahl der Matches
            int lRest = knownWord.length() % blockSize; // Anzahl der Zeichen in der Letzten Zeile
            int lRows = known.getLineLength();  // Anzahl der Zeilen
            int mOffset = -1;                   // offset für m bei Misserfolg
            firstMatchRow = 0;                  // position of first match
            int l = 0;                          // l-Zeilen in der knownBM
            int j = 0;                          // j-Spalten in der knownBM
            boolean foundFirst = false;

            for (int i = 0; i < bm.getLineLength() - lRows; i++) {  // i-Zeilen der BM
                if (found)
                    break;
                for (int m = 0; m < bm.getBlockLength(); m++) {     // m-Spalten in der BM
                    if (foundCount >= knownWord.length()) {         // nach Gefunden wird alles neuinit -> zum Testen
                        System.out.println("-> GEFUNDEN !!!");
//                        m = mOffset + 1;
//                        l = 0;
//                        foundCount = 0;
                        found = true;
                        break;
                    }
                    if (foundFirst && i > firstMatchRow + 1) {      // wenn keine Zeichen in der (relativ) 2-ten Spalte gefunden werden -> fange die Suche neu an
                        i--;
                        l = 0;
                        m = -1;
                        folge.clear();
                        foundCount = 0;
                        firstMatchRow = 0;
                        foundFirst = false;
                        continue;
                    }
                    if (bm.getArray()[i][m] == known.getArray()[j][l]) {
                        if (foundCount <= 0) {
                            firstMatchRow = i;
                            foundFirst = true;
                        }
                        folge.add(m);
                        foundCount++;
                        if ((lRest != 0 && l < lRest) || (lRest == 0 && l < blockSize)) {   // l soll kleiner als der lRest
                            for (int lRow = 1; lRow < lRows; lRow++)                        // es wird nach dem vertikalen knownBM-Block gesucht
                                if (known.getArray()[j + lRow][l] != '\0') {                // wenn untergeortnetes Zeichen existiert
                                    if (bm.getArray()[i + lRow][m] == known.getArray()[j + lRow][l]) {
                                        foundCount++;
                                    } else {            // nicht gefunden -> mOffset++; wieder Suchen
                                        l = 0;
                                        mOffset++;
                                        folge.clear();
                                        foundCount = 0;
                                        firstMatchRow = 0;
                                        foundFirst = false;
                                    }
                                } else System.out.println("KnownBM \\0-Zelle erreicht !");
                        } else if (lRest != 0 && l < blockSize) {
                            for (int lRow = 1; lRow < lRows - 1; lRow++)                    // es wird nach dem vertikalen knownBM-Block gesucht
                                if (known.getArray()[j + lRow][l] != '\0') {                // wenn untergeortnetes Zeichen existiert
                                    if (bm.getArray()[i + lRow][m] == known.getArray()[j + lRow][l]) {
                                        foundCount++;
                                    } else {            // nicht gefunden -> mOffset++; wieder Suchen
                                        l = 0;
                                        mOffset++;
                                        folge.clear();
                                        foundCount = 0;
                                        firstMatchRow = 0;
                                        foundFirst = false;
                                    }
                                } else System.out.println("KnownBM \\0-Zelle erreicht !");
                        } else {
                            System.out.println("TODO !"); //TODO: soll überprüft werden -> was soll hier rein?
                        }
                        l++;
                        m = mOffset;
                    }
                }
            }
            if (found) {
                System.out.print("TR-Folge: ");
                for (int i = 0; i < folge.size(); ++i)
                    System.out.print(folge.get(i) + " ");
                System.out.println("\n lineBreak: " + lineBreak);
                return true;
            }
        }
        // Fall 2: Wir testen eine Block-Länge von knownWord.length()+1 bis knownWord.length()+k -----------------------
        if (!found) {
            for (int k = bm.getBlockLength() + 1; k < bm.getBlockLength() + 10; k++) {
                //TODO: ...
            }
        }
        return false;
    }
}