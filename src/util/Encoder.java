/**
 *
 */
package util;

import java.io.File;
import java.util.Random;

//TODO Complete this class
public class Encoder {

    private final String SOURCE_FILE_NAME;
    private final String DEST_FILE_NAME;
    private final Transposition TRANSPOSITION;

    /**
     * Kodiert eine Datei mit der gegebenen Transposition um so ein Beispiel zu
     * erhalten Vorgaben der Aufgabe sind: 10 Blöcke je Zeile....
     *
     * @param transposition
     * @param sourceFileName
     */
    public Encoder(String transposition, String sourceFileName) {
        if (sourceFileName != null) {
            this.SOURCE_FILE_NAME = sourceFileName;
            if (sourceFileName.contains(".txt")) {
                this.DEST_FILE_NAME = sourceFileName.replace(".txt", "encode.txt");
            } else {
                this.DEST_FILE_NAME = sourceFileName + "encode.txt";
            }
        } else {
            this.SOURCE_FILE_NAME = "";
            this.DEST_FILE_NAME = "";
        }
        TRANSPOSITION = new Transposition(transposition);
    }

    public int encodeFile() {
        int returnCode = 0;

        TextFileHandler inFileText = new TextFileHandler(SOURCE_FILE_NAME);
        String text = inFileText.readText();

        if (text != null) {
            if (!text.isEmpty()) {
                text = replaceAndFill(text);
                BlockMatrix column = new BlockMatrix(text.toCharArray(), this.TRANSPOSITION, true);
                column.transpose();
                returnCode = writeEncodedText(column);
            } else {
                return -1;
            }

        } else {
            returnCode = -1;
        }

        return returnCode;
    }

    /**
     * Ersetze unerwünschte Zeichen aus String und fülle Blöcke auf, damit keine
     * allein stehenden Zeichen im Geheimtext enstehen
     *
     * @param text
     * @return
     */
    private String replaceAndFill(String text) {
        // anschließend ersetze alle unerwünschten zeichen in der Datei
        text = text.replaceAll(("[^0-9a-zA-Z]"), "");

        // Fülle mit zeichen auf, falls ungerade zeichenanzahl,
        int product = TRANSPOSITION.getBlockLength() * 5; // 5 = Buchstabe je
        // Block
        int charCount = 0;

        if (TRANSPOSITION.getBlockLength() > 0) {
            if ((text.length() % product) != 0) { // Muss aufgefüllt werden?
                int factor = (text.length() / product) + 1;
                charCount = (product * factor) - text.length(); // Anzahl der
                // nötigen
                // Zeichen
            }
        }
        if (charCount > 0) {
            for (int i = 0; i < charCount; i++) {
                text += (char) (new Random().nextInt(25) + 65);
            }
        }
        return text;
    }

    /**
     * Schreibt codierten Text in die Datei, entsprechend den Vorgaben der
     * Aufgabe
     *
     * @param column
     * @return
     */
    private int writeEncodedText(BlockMatrix column) {
        if (DEST_FILE_NAME != "encode.txt") {
            File file = new File(DEST_FILE_NAME);
            if (file.exists())
                file.delete();
            TextFileHandler outFileHandler = new TextFileHandler(DEST_FILE_NAME);
            SecretFormatter secretFormatter = new SecretFormatter(column, 5, 10);
            boolean check = outFileHandler.writeText(secretFormatter.getSecret());
            if (check)
                return 0;
            else
                return -1;
        } else
            return -1;
    }
}
