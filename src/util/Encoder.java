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

			try {
				// TODO mehrere zeilen aus Datei lesen
				text = reader.readLine();

				// anschließend ersetze alle unerwünschten zeichen in der Datei
				text = text.replaceAll(("[^0-9a-zA-Z]"), "");
				// Fülle mit zeichen auf, falls ungerade zeichenanzahl,
				// TODO dabei muss zeichenanzahl immer stimmen!!
				
				if (TRANSPOSITION.getBlockLength() != 0) {
					int toInsert = text.length() % TRANSPOSITION.getBlockLength();
					if (toInsert > 0) {
						final String alphabet = "abcdefghijklmopqrstuvwxyz";
						final int N = alphabet.length();

						Random r = new Random();

						for (int i = 0; i < toInsert; i++) {
							text += alphabet.charAt(r.nextInt(N));
						}
					}
				}
				

			} catch (IOException e1) {
				System.out.println("FileReadLineError");
				returnCode = -1;
			}

			final BlockMatrix column = new BlockMatrix(text.toCharArray(), this.TRANSPOSITION);
			column.transpose();

			returnCode = writeEncodedText(bufferedWriter, column);

			System.out.println(Arrays.deepToString(column.getArray()));

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
	 * Schreibt codierten Text in die Datei, entsprechend den Vorgaben der Aufgabe
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
