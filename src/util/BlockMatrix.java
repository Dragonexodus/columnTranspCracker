package util;

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
		// Anmerkung: Warum braucht BlockMatrix BlockLänge,
		// wo die BlockLänge doch in der Transposition hinterlegt ist?
		this.T = t;
		// this.BLOCK_LENGTH = blockLength;

		if (t.getBlockLength() > 0) {
			// Prüfe auf Rest, zur Ermittlung der Spaltenlänge
			if (text.length % t.getBlockLength() > 0) {
				this.LINE_LENGTH = text.length / (t.getBlockLength() + 1);
			} else {
				this.LINE_LENGTH = text.length / t.getBlockLength();
			}
		} else {
			this.LINE_LENGTH = 0;
		}

		// Initialisiere Block-Matrix
		this.BLOCK_MATRIX = new char[this.LINE_LENGTH][t.getBlockLength()];

		// Fülle Block-Matrix mit Geheimnistext
		for (int i = 0; i < this.LINE_LENGTH; i++) {

			for (int j = 0; j < t.getBlockLength(); j++) {

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
				
				// die Spalten in der Matrix werden in der Abhängigkeit von der Abbildung getauscht
				for (int j = 0; j < temp.length; j++) {
					BLOCK_MATRIX[j][i] = BLOCK_MATRIX[j][T.getTransposition().get(i)];
					BLOCK_MATRIX[j][T.getTransposition().get(i)] = temp[j];
				}
			}
		}
		System.out.println("bla");
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
