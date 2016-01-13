package util;

public class BlockMatrix {
	
	private final int BLOCK_LENGTH;
	private final int LINE_LENGTH;
	private final char[][] BLOCK_MATRIX;
	private final Transposition T;
	
	/**
	 * Block Matrix zur Kodierung eines Klartextes.
	 * 
	 * @param blockLength Blocklänge
	 * @param text Klartext
	 */
	public BlockMatrix(int blockLength, char[] text, Transposition t){
		//Anmerkung: Warum braucht BlockMatrix BlockLänge, 
		//wo die BlockLänge doch in der Transposition hinterlegt ist?
		this.T = t;
		this.BLOCK_LENGTH = blockLength;
		
		
		if(this.BLOCK_LENGTH > 0){
			//Prüfe auf Rest, zur Ermittlung der Spaltenlänge
			if(text.length % this.BLOCK_LENGTH > 0){
				this.LINE_LENGTH = text.length / (BLOCK_LENGTH+1);
			}
			else{
				this.LINE_LENGTH = text.length / BLOCK_LENGTH;
			}
		}else{
			this.LINE_LENGTH = 0;
		}
		
		//Initialisiere Block-Matrix
		this.BLOCK_MATRIX = new char[this.LINE_LENGTH][this.BLOCK_LENGTH];
		
		//Fülle Block-Matrix mit Geheimnistext		
		for(int i=0; i < this.LINE_LENGTH;i++){
			
			for(int j=0; j < this.BLOCK_LENGTH; j++){
				
				BLOCK_MATRIX[i][j] = text[i*BLOCK_LENGTH + j];
			}
		}		
	}	
	
	public void transpose(){
		//so eine FUnktion in der Transposition Klassen? und das hier private ?
	}

	public int getBlockLength() {
		return BLOCK_LENGTH;
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
