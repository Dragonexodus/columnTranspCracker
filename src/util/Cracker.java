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

    //TODO: Test
//    String tempTr = "1,0,3,2,5,4";
    String tempTr = "0,1,3,5,2,4";

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

    public void testTransposition() {

        for (int i = 0; i < kandidaten.size(); i++) {

            TranspositionListe trList = kandidaten.get(i).getPermutationList();

            for (int j = 0; j < trList.size(); j++) {           // alle Transpositionen in dem Kandidat

                int q = 0;
                int kCount = 0;
                int foundCount = 0;
                boolean found = false;
                System.out.println("Kandidat: " + i + ", TrNr: " + j + ", Tr: " + trList.get(i) + ", Blocklänge: " + kandidaten.get(i).getBlockSize());

                boolean testFlag = true;
                Transposition t = null;
                if (testFlag && kandidaten.get(i).getBlockSize() == 6)
                    t = new Transposition(tempTr);
                else
                    t = new Transposition(trList.get(i));

                BlockMatrix temp = new BlockMatrix(SECRET.toCharArray(), t, false);

                if (testFlag && kandidaten.get(i).getBlockSize() == 6) {
                    temp.transpose();
                    temp.setTr(new Transposition(trList.get(i)));
                }
                temp.transpose();

                ArrayList<Integer> testList = new ArrayList<>(trList.get(i).size());
                for (int k = 0; k < trList.get(i).size(); k++)
                    testList.add(k);

                for (int n = 0; n < trList.get(i).size() + 1; n++) { // max block.size Rotationen

                    Collections.rotate(testList, 1);
                    BlockMatrix test = new BlockMatrix(SECRET.toCharArray(), new Transposition(testList), false);
                    temp.copyMatrix(test);
                    test.transpose();
                    kCount++;

                    for (int k = 0; k < test.getLineLength(); k++) {
                        if (found)
                            break;
                        for (int l = 0; l < test.getBlockLength(); l++) {

                            if (foundCount >= knownWord.length()) {
                                System.out.println("-> Tr GEFUNDEN: " + trList.get(j));

//                                kCount--;
                                System.out.println("SHIFT: " + kCount);
                                Collections.rotate(trList.get(j), kCount);
                                System.out.println("DeKey: " + trList.get(j));

                                enKey = new ArrayList<>(trList.get(j).size());
                                for (int m = 0; m < trList.get(j).size(); m++)
                                    enKey.add(trList.get(j).indexOf(m));
                                System.out.println("EnKey: " + enKey);
                                found = true;
                                break;
                            }
                            if (test.getArray()[k][l] == knownWord.charAt(q)) {
                                q++;
                                foundCount++;
                            } else {
                                q = 0;
                                foundCount = 0;
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean crackByKnownWord() {

        // Fall 1: Wir testen eine Block-Länge von 2 bis knownWord.length() --------------------------------------------
        for (int k = 2; k < 21; k++) {

            if (SECRET.length() % k != 0) // hier werden nur passende Blockgrößen durchgelassen
                continue;

            //TODO: Test
            Transposition t;
            if (k == 6)
                t = new Transposition(tempTr);
            else
                t = new Transposition(k);

            this.bm = new BlockMatrix(this.SECRET.toCharArray(), t, false);
            bm.transpose(); //TODO: Test

            //TODO: Test
            if (false && k == 6) {
                t = new Transposition("0, 1, 4, 2, 5, 3");
                bm.setTr(t);
                bm.transpose();
            }
            System.out.println(SECRET);
            bm.print();

            BlockMatrix known = new BlockMatrix(knownWord.toCharArray(), t, true);
            known.print();
            System.out.println("Blocklänge: " + k + "\n");

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

            if (k < knownWord.length()) {
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
                                System.out.println("Permutationen: " + kandidat.getPermutationList().size());

//                                kandidat.printPermutationList(); //TODO

                                kandidat = null;
                            }
                        if (m >= blockSize)
                            m = 0;
                        if (i >= bm.getLineLength() - lRows)
                            break;
                        if (l >= blockSize)
                            System.out.println("ERROR: Cracker: crackByKnownWord(): l to long !");

                        if (kandidat != null)                           // wenn keine Zeichen in der (relativ) 2-ten Zeile gefunden werden -> fange die Suche neu an
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
                                kandidat = new Kandidat(Kandidat.TYPE.CASE_1);
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
//                                zeichen.print();
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
            } else { // Fall 2: Wir testen eine Block-Länge von knownWord.length() bis k

                int iOld = 0;

                for (int i = 0; i < bm.getLineLength(); i++) {
                    for (int m = 0; m < bm.getBlockLength() + 1; m++) {

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
//                                kandidat.print();
                                System.out.println("Permutationen: " + kandidat.getPermutationList().size());

//                                kandidat.printPermutationList(); //TODO

                                kandidat = null;
                            }
                        if (m >= blockSize)
                            m = 0;
                        if (i >= bm.getLineLength() - 1)
                            break;
                        if (l >= knownWord.length()) {
                            System.out.println("ERROR: Cracker: crackByKnownWord(): l to long ! -2");
                        }

                        if (kandidat != null)
                            if (i > kandidat.getFirstMatchRow() + 1) {
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

                        if (bm.getArray()[i][m] == knownWord.charAt(l)) {
                            if (kandidat == null) {
                                kandidat = new Kandidat(Kandidat.TYPE.CASE_2);
                                zeichenListe = new ZeichenListe();
                            }
                            if (zeichen == null)
                                zeichen = new Zeichen(bm.getArray()[i][m], m);
                            if (zeichenListe == null)
                                zeichenListe = new ZeichenListe();
                            if (zeichenListe.size() <= 0)
                                foundCount++;

                            if (zeichen != null) {
                                zeichenListe.add(zeichen);
                                if (kandidat.getFoundCount() <= 0)
                                    kandidat.setFirstMatch(blockSize, i, m);
                                kandidat.setFoundCount(kandidat.getFoundCount() + foundCount);
                                kandidat.appendSave(zeichenListe);
                                firstMatch = true;
                                foundCount = 0;
//                                zeichen.print();
                                zeichen = null;
                            }
                        }
                        if (m >= blockSize - 1) {
                            if (firstMatch) {
//                                    if (i < kandidat.getFirstMatchRow() + 1)
//                                        i++;
//                                    else {
                                l++;
                                firstMatch = false;
                                zeichenListe = null;
//                                    }
                            } else
                                i++;
                        }
                    }
                }
            }
        }
        System.out.println("Kandidaten: " + kandidaten.size());
        return true;
    }
}
