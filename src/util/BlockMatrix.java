package util;

public class BlockMatrix {

    // private final int BLOCK_LENGTH;
    private final int LINE_LENGTH;
    private char[][] BLOCK_MATRIX;
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
            System.out.println("--------------------");
            System.out.println("Länge PLAIN-Text: \t" + text.length);
            System.out.println("Länge BLOCK: \t\t" + t.getBlockLength());
            // Ermitteln der Spaltenlänge
            if (text.length % t.getBlockLength() == 0)
                this.LINE_LENGTH = text.length / t.getBlockLength();        // Geheimtext-Matrix
            else
                this.LINE_LENGTH = (text.length / t.getBlockLength()) + 1;  // Klartext-Matrix
        } else
            this.LINE_LENGTH = 0;

        System.out.println("Zeilenanzahl: \t\t" + LINE_LENGTH);
        // Initialisiere Block-Matrix
        this.BLOCK_MATRIX = new char[this.LINE_LENGTH][t.getBlockLength()];

        if (!klartextMatrix)    // Geheimtext-Matrix
            for (int i = 0; i < t.getBlockLength(); i++) {      // Spalte
                for (int j = 0; j < this.LINE_LENGTH; j++) {    // Zeile

                    // Ermitteln der aktuellen Position in PLAIN-Text Zeichenkette
                    if (i * LINE_LENGTH + j < text.length)
                        BLOCK_MATRIX[j][i] = text[i * this.LINE_LENGTH + j];
                    else
                        System.out.println("ERROR: BlockMatrix: Konstruktor !");
                }
            }
        else                    // Klartext-Matrix
            for (int i = 0; i < this.LINE_LENGTH; i++) {        // Zeile
                for (int j = 0; j < t.getBlockLength(); j++) {  // Spalte

                    // Ermitteln der aktuellen Position in PLAIN-Text Zeichenkette
                    if (i * t.getBlockLength() + j < text.length)
                        BLOCK_MATRIX[i][j] = text[i * t.getBlockLength() + j];
                    else
                        BLOCK_MATRIX[i][j] = '_';
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

    public void print() {
        for (int i = 0; i < LINE_LENGTH; i++) {
            for (int j = 0; j < getBlockLength(); j++) {
                System.out.print(BLOCK_MATRIX[i][j] + " ");
            }
            System.out.println();
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

    public char[][] getArray() {
        return BLOCK_MATRIX;
    }

    public Transposition getTransposition() {
        return T;
    }
}
