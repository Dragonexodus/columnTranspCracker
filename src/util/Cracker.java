package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cracker {

    private BlockMatrix bm;
    private final String SECRET;
    private String plain;

    KandidatListe kandidaten;
    String knownWord;
    List<Integer> deKey;
    List<Integer> enKey;
    int firstMatchRow;

    public Cracker(String secret, String knownWord) {
        this.SECRET = secret;
        this.knownWord = knownWord;
        this.plain = "";
        deKey = new ArrayList<Integer>();
        kandidaten = new KandidatListe();
    }

    public String getCrackedSecret() {

        for (int i = 0; i < bm.getLineLength(); i++)
            for (int m = 0; m < bm.getBlockLength(); m++)
                plain += bm.getArray()[i][m];

        return this.plain;
    }

    public boolean testTransposition() {

        int foundCount = 0;
        int kCount = 0;
        int l = 0;

        List<Integer> tempList = new ArrayList<>(bm.getBlockLength());
        for (int i = 0; i < deKey.size(); i++)
            tempList.add(i);

        String str = deKey.toString();
        str = str.replaceAll(("[\\[\\] ]"), "");
        System.out.println(str);

        bm.setTr(new Transposition(str));
        bm.transpose();

        for (int k = 0; k < bm.getBlockLength() + 1; k++) {

            str = tempList.toString();
            str = str.replaceAll(("[\\[\\] ]"), "");
            Collections.rotate(tempList, 1);
            System.out.println("testTR: " + str);

            BlockMatrix test = new BlockMatrix(SECRET.toCharArray(), new Transposition(str), false);
            for (int i = 0; i < bm.getLineLength(); i++)
                for (int j = 0; j < bm.getBlockLength(); j++)
                    test.getArray()[i][j] = bm.getArray()[i][j];
            test.transpose();
            kCount++;

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
                        System.out.println("-> TR GEFUNDEN: " + deKey);

                        kCount--;
                        System.out.println("SHIFT: " + kCount);
                        Collections.rotate(deKey, kCount);
                        System.out.println("DeKey: " + deKey);

                        enKey = new ArrayList<>(deKey.size());
                        for (int q = 0; q < deKey.size(); q++)
                            enKey.add(deKey.indexOf(q));
                        System.out.println("EnKey: " + enKey);

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

        // Fall 1: Wir testen eine Block-Länge von 2 bis knownWord.length() --------------------------------------------
        for (int k = 2; k < knownWord.length() + 1; k++) {

            if (SECRET.length() % k != 0) // hier werden nur passende Blockgrößen durchgelassen
                continue;

            //TODO: Tetst
            Transposition t;
            if (k == 6)
//                t = new Transposition("0,1,2,3,4,5");
                t = new Transposition("5,4,3,2,1,0");
            else
                t = new Transposition(k);

            this.bm = new BlockMatrix(this.SECRET.toCharArray(), t, false);
            bm.transpose(); //TODO: Test

            //TODO: Test
            if (false && k == 6) {
                t = new Transposition("3,1,0,5,4,2");
                bm.setTr(t);
                bm.transpose();
            }
            System.out.println(SECRET);
            bm.print();

            BlockMatrix known = new BlockMatrix(knownWord.toCharArray(), t, true);
            known.print();

            // Init -----------------------------------------------------------
            int blockSize = k;
            int foundCount = 0;                 // Anzahl der Matches
            boolean firstMatch = false;
            int lRest = knownWord.length() % blockSize; // Anzahl der Zeichen in der Letzten Zeile
            int lRows = known.getLineLength();  // Anzahl der Zeilen
            firstMatchRow = 0;                  // position of first match
            int l = 0;                          // l-Zeilen in der knownBM
            int j = 0;                          // j-Spalten in der knownBM
            Kandidat kandidat = null;
            ZeichenListe zeichenListe = null;
            Zeichen zeichen = null;

            for (int i = 0; i < bm.getLineLength() - lRows; i++) {  // i-Zeilen der BM
                for (int m = 0; m < blockSize + 1; m++) {           // m-Spalten in der BM

                    if (kandidat != null)
                        if (kandidat.getFoundCount() >= knownWord.length() && m >= blockSize) { // nach Gefunden wird alles neuinit -> zum Testen
                            System.out.println("-> GEFUNDEN !!!");
                            kandidaten.add(kandidat);
                            l = 0;
                            i = kandidat.getFirstMatchRow();
                            if (kandidat.getFirstMatchCol() + 1 < blockSize)
                                m = kandidat.getFirstMatchCol() + 1;
                            else {
                                i++;
                                m = -1;
                            }
                            foundCount = 0;
                            kandidat.print();
                            System.out.println("Permutationen: " + kandidat.getPermutations());

                            kandidat.printPermutationList(kandidat.getPermutationList()); //TODO

                            kandidat = null;
                        }
                    if (m >= blockSize)
                        m = 0;
                    if (i >= bm.getLineLength() - lRows)
                        break;
                    if (l >= blockSize)
                        System.out.println("ERROR: Cracker: crackByKnownWord(): l to long !");

                    if (kandidat != null)                           // wenn keine Zeichen in der (relativ) 2-ten Spalte gefunden werden -> fange die Suche neu an
                        if (kandidat.getFoundCount() > 0 && i > kandidat.getFirstMatchRow() + lRows - 1) {
                            l = 0;
                            i = kandidat.getFirstMatchRow();
                            if (kandidat.getFirstMatchCol() + 1 < blockSize)
                                m = kandidat.getFirstMatchCol() + 1;
                            else {
                                i++;
                                m = -1;
                            }
                            foundCount = 0;
                            kandidat = null;
                        }
                    if (bm.getArray()[i][m] == known.getArray()[j][l]) {
                        if (kandidat == null) {
                            kandidat = new Kandidat();
                            zeichenListe = new ZeichenListe();
                        }
                        if (zeichen == null)
                            zeichen = new Zeichen(bm.getArray()[i][m], m);

                        if (zeichenListe == null)
                            zeichenListe = new ZeichenListe();

                        if (zeichenListe.size() <= 0)
                            foundCount++;

                        if ((lRest != 0 && l < lRest) || (lRest == 0 && l < blockSize)) {   // l soll kleiner als der lRest sein
                            for (int lRow = 1; lRow < lRows; lRow++)                        // es wird nach dem vertikalen knownBM-Block gesucht
                                if (bm.getArray()[i + lRow][m] == known.getArray()[j + lRow][l]) {
                                    if (zeichenListe.size() <= 0)
                                        foundCount++;
                                } else {                                                    // nicht gefunden -> mOffset++; wieder Suchen
                                    foundCount = 0;
                                    zeichen = null;
                                    break;
                                }
                        } else if (lRest != 0 && l < blockSize) {
                            for (int lRow = 1; lRow < lRows - 1; lRow++)                    // es wird nach dem vertikalen knownBM-Block gesucht
                                if (bm.getArray()[i + lRow][m] == known.getArray()[j + lRow][l]) {
                                    if (zeichenListe.size() <= 0)
                                        foundCount++;
                                } else {                                                    // nicht gefunden -> mOffset++; wieder Suchen
                                    foundCount = 0;
                                    zeichen = null;
                                    break;
                                }
                        } else
                            System.out.println("ERROR: no enter !");

                        if (zeichen != null) {
                            zeichenListe.add(zeichen);
                            if (kandidat.getFoundCount() <= 0)
                                kandidat.setFirstMatch(blockSize, i, m);
                            kandidat.setFoundCount(kandidat.getFoundCount() + foundCount);
                            kandidat.appendSave(zeichenListe);
                            firstMatch = true;
                            foundCount = 0;
                            zeichen.print();
                            zeichen = null;
                        }
                    }
                    if (m >= blockSize - 1) {
                        if (firstMatch) {
                            l++;
                            firstMatch = false;
                            zeichenListe = null;
                        } else {
                            i++;
                        }
                    }
                }
            }
        }
        // Fall 2: Wir testen eine Block-Länge von knownWord.length()+1 bis knownWord.length()+k -----------------------
        if (true) {
            for (int k = bm.getBlockLength() + 1; k < bm.getBlockLength() + 10; k++) {
                //TODO: ...
            }
        }
        return false;
    }
}
