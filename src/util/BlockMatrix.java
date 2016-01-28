package util;

import java.util.ArrayList;

public class BlockMatrix {

    // private final int BLOCK_LENGTH;
    private final int LINE_LENGTH;
    private char[][] BLOCK_MATRIX;
    private String content;
    private Transposition T;

    /**
     * Block Matrix zur Kodierung eines Klartextes.
     * Für Text und KnownWord angepasst...
     *
     * @param text Klartext
     */
    public BlockMatrix(char[] text, Transposition t, boolean klartextMatrix) {

        this.T = t;

        if (t.getBlockLength() > 0) {
            if (text.length % t.getBlockLength() == 0)                      // Ermitteln der Spaltenlänge
                this.LINE_LENGTH = text.length / t.getBlockLength();        // Geheimtext-Matrix
            else
                this.LINE_LENGTH = (text.length / t.getBlockLength()) + 1;  // Klartext-Matrix
        } else
            this.LINE_LENGTH = 0;

        
        System.out.println("--------------------");
        System.out.println("Länge PLAIN-Text: \t" + text.length);
        System.out.println("Länge BLOCK: \t\t" + t.getBlockLength());
        System.out.println("Zeilenanzahl: \t\t" + LINE_LENGTH);
        

        this.BLOCK_MATRIX = new char[this.LINE_LENGTH][t.getBlockLength()]; // Initialisiere Block-Matrix

        if (!klartextMatrix)    // Geheimtext-Matrix
        {
        	for (int column = 0; column < t.getBlockLength(); column++) {   // Spalte
        		
                for (int row = 0; row < this.LINE_LENGTH; row++) {    		// Zeile

                    // Ermitteln der aktuellen Position in PLAIN-Text Zeichenkette
                	
                    if (column * LINE_LENGTH + row < text.length)
                        BLOCK_MATRIX[row][column] = text[column * this.LINE_LENGTH + row];
                    else
                        System.out.println("ERROR: BlockMatrix: Konstruktor !");
                }
            }
        }
        
        else {	// Klartext-Matrix
        	
        	for (int row = 0; row < this.LINE_LENGTH; row++) {        // Zeile
                for (int column = 0; column < t.getBlockLength(); column++) {  // Spalte

                    // Ermitteln der aktuellen Position in PLAIN-Text Zeichenkette
                    if (row * t.getBlockLength() + column < text.length)
                        BLOCK_MATRIX[row][column] = text[row * t.getBlockLength() + column];
                    else
                        BLOCK_MATRIX[row][column] = '_';
                }
            }
        }            
    }

    public void transpose() {

        char temp[][] = new char[LINE_LENGTH][T.getBlockLength()];

        for (int i = 0; i < T.getBlockLength(); i++) {
            for (int j = 0; j < LINE_LENGTH; j++) {
                temp[j][i] = BLOCK_MATRIX[j][T.getTransposition().get(i)];
            }
        }
        BLOCK_MATRIX = temp;
    }

    public void transpose(ArrayList<Integer> list) {

        char temp[][] = new char[LINE_LENGTH][list.size()];

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < LINE_LENGTH; j++) {
                temp[j][i] = BLOCK_MATRIX[j][list.get(i)];
            }
        }
        BLOCK_MATRIX = temp;
    }

    public void transpose2() {
        for (int i = 0; i < T.getBlockLength(); i++) {
            if (i != T.getTransposition().get(i)) {

                // eine Kopie von der Spalte erstellen
                char temp[] = new char[LINE_LENGTH];
                for (int j = 0; j < temp.length; j++)
                    temp[j] = BLOCK_MATRIX[j][i];

                // die Spalten in der Matrix werden in der Abhängigkeit von der
                // Abbildung getauscht
                for (int j = 0; j < temp.length; j++) {
                    BLOCK_MATRIX[j][i] = BLOCK_MATRIX[j][T.getTransposition().get(i)];
                    BLOCK_MATRIX[j][T.getTransposition().get(i)] = temp[j];
                }
            }
        }
    }

    public void copyMatrix(BlockMatrix m) {
        if (LINE_LENGTH > 0 && T.getBlockLength() > 1)
            for (int i = 0; i < LINE_LENGTH; i++)
                for (int j = 0; j < T.getBlockLength(); j++)
                    m.getArray()[i][j] = BLOCK_MATRIX[i][j];
        else
            System.out.println("ERROR: BlockMatrix: copyMatrix(): Blockgröße oder Zeilenanzahl ist falsch !");
    }

    public void print() {
        for (int i = 0; i < LINE_LENGTH; i++) {
            for (int j = 0; j < getBlockLength(); j++) {
                System.out.print(BLOCK_MATRIX[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void setTr(Transposition t) {
        T = t;
    }

    public int getBlockLength() {
        return T.getBlockLength();
    }

    public int getLineLength() {
        return LINE_LENGTH;
    }
    
    public String getColumn(int  col) {
    	
    	if(col > this.getBlockLength()) throw new IndexOutOfBoundsException();
    	else {
    		
    		return this.content.substring(col * this.LINE_LENGTH, ((col+1) * this.LINE_LENGTH) - 1);
    	}    	
    }

    public char[][] getArray() {
        return BLOCK_MATRIX;
    }

    public Transposition getTransposition() {
        return T;
    }
}
