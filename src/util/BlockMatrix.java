package util;

import java.util.Arrays;
import java.util.Random;

public class BlockMatrix {

	// private final int BLOCK_LENGTH;
	private final int LINE_LENGTH;
	private final char[][] BLOCK_MATRIX;
	private final Transposition T;

	/**
	 * Block Matrix zur Kodierung eines Klartextes.
	 * 
	 * @param blockLength
	 *            Blocklänge
	 * @param text
	 *            Klartext
	 */
	public BlockMatrix(char[] text, Transposition t) {

		this.T = t;

		if (t.getBlockLength() > 0) {
			System.out.println("Länge PLAIN-Text: \t" + text.length);
			System.out.println("Länge BLOCK: \t\t" + t.getBlockLength());
			// Ermitteln der Spaltenlänge
			this.LINE_LENGTH = text.length / t.getBlockLength();
		} else
			this.LINE_LENGTH = 0;

		System.out.println("Länge Spalten: \t\t" + LINE_LENGTH);
		// Initialisiere Block-Matrix
		this.BLOCK_MATRIX = new char[this.LINE_LENGTH][t.getBlockLength()];

		// Fülle Block-Matrix mit Geheimnistext
		for (int i = 0; i < this.LINE_LENGTH; i++) {
			for (int j = 0; j < t.getBlockLength(); j++) {
				// Ermitteln der aktuellen Position in PLAIN-Text Zeichenkette
				BLOCK_MATRIX[i][j] = text[i * t.getBlockLength() + j];
			}
		}
	}

	public void transpose() {
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
