/**
 * 
 */
package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
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

		BufferedReader reader = null;
		BufferedWriter bufferedWriter = null;
		try {
			reader = new BufferedReader(new FileReader(SOURCE_FILE_NAME));
			bufferedWriter = new BufferedWriter(new FileWriter(DEST_FILE_NAME));
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound: Source");
			returnCode = -1;
		} catch (IOException e) {
			System.out.println("FileNotFound: Desti");
			returnCode = -1;
		}

		if ((reader != null) && bufferedWriter != null) {
			String text = "";

			try { // Read file
				String lineReaded = null;
				do {
					lineReaded = reader.readLine();
					if (lineReaded != null) {
						text += lineReaded;
					}
				} while (lineReaded != null);
			} catch (IOException e1) {
				System.out.println("FileReadLineError");
				returnCode = -1;
			}

			text = replaceAndFill(text);

			final BlockMatrix column = new BlockMatrix(text.toCharArray(), this.TRANSPOSITION);
			
			column.transpose();
			returnCode = writeEncodedText(bufferedWriter, column);

			try {
				bufferedWriter.close();
				reader.close();
			} catch (IOException e1) {
				System.out.println("Closing files failed");
				returnCode = -1;
			}
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
	 * @param bufferedWriter
	 * @param colum
	 * @return
	 */
	private int writeEncodedText(BufferedWriter bufferedWriter, BlockMatrix column) {
		int returnCode = 0;
		final char[][] matrix = column.getArray();
		try {
			long counterSpace = 0;
			int counterLine = 0;
			for (int spalte = 0; spalte < column.getBlockLength(); spalte++) {
				for (int zeile = 0; zeile < column.getLineLength(); zeile++) {
					bufferedWriter.write(matrix[zeile][spalte]);
					counterSpace++;
					if (counterSpace % 5 == 0) {
						if (counterLine == 9) {
							bufferedWriter.write("\n");
							counterLine = 0;
							counterSpace = 0;
						} else {
							counterLine++;
							bufferedWriter.write(" ");
						}
					}
				}
			}
		} catch (IOException e1) {
			System.out.println("Writing file failed");
			returnCode = -1;
		}
		return returnCode;
	}
}
