package util;

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
     * @param blockLength Blocklänge
     * @param text        Klartext
     */
    public BlockMatrix(char[] text, Transposition t, boolean klartextMatrix) {

        this.T = t;

        if (t.getBlockLength() > 0) {
            System.out.println("--------------------");
            System.out.println("Länge PLAIN-Text: \t" + text.length);
            System.out.println("Länge BLOCK: \t\t" + t.getBlockLength());
            // Ermitteln der Spaltenlänge
            if (text.length % t.getBlockLength() == 0)
                this.LINE_LENGTH = text.length / t.getBlockLength();
            else
                this.LINE_LENGTH = (text.length / t.getBlockLength()) + 1;
        } else
            this.LINE_LENGTH = 0;

        System.out.println("Zeilenanzahl: \t\t" + LINE_LENGTH);
        // Initialisiere Block-Matrix
        this.BLOCK_MATRIX = new char[this.LINE_LENGTH][t.getBlockLength()];

        if (!klartextMatrix) // Geheimtextmatrix
            for (int i = 0; i < t.getBlockLength(); i++) {      // Spalte
                for (int j = 0; j < this.LINE_LENGTH; j++) {    // Zeile

                    // Ermitteln der aktuellen Position in PLAIN-Text Zeichenkette
                    if (i * t.getBlockLength() + j < text.length)
                        BLOCK_MATRIX[j][i] = text[i * this.LINE_LENGTH + j];
                    else
                        BLOCK_MATRIX[j][i] = '\0';
                }
            }
        else    // knownWordMatrix
            for (int i = 0; i < this.LINE_LENGTH; i++) {
                for (int j = 0; j < t.getBlockLength(); j++) {
                    // Ermitteln der aktuellen Position in PLAIN-Text Zeichenkette
                    if (i * t.getBlockLength() + j < text.length)
                        BLOCK_MATRIX[i][j] = text[i * t.getBlockLength() + j];
                    else
                        BLOCK_MATRIX[i][j] = '\0';
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
