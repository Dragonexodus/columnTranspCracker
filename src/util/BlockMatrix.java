package util;

public class BlockMatrix {
	
	final int BLOCK_LENGTH;
	final int LINE_LENGTH;
	final char[][] BLOCK_MATRIX;
	
	
	public BlockMatrix(int blockLength, char[] secret){
		
		this.BLOCK_LENGTH = blockLength;
		
		//Prüfe auf Rest, zur Ermittlung der Spaltenlänge
		if(secret.length % this.BLOCK_LENGTH > 0){
			this.LINE_LENGTH = secret.length / (BLOCK_LENGTH+1);
		}
		else{
			this.LINE_LENGTH = secret.length / BLOCK_LENGTH;
		}
		
		//Initialisiere Block-Matrix
		this.BLOCK_MATRIX = new char[this.LINE_LENGTH][this.BLOCK_LENGTH];
		
		//Fülle Block-Matrix mit Geheimnistext
		for(int i=0; i < this.LINE_LENGTH;i++){
			for(int j=0; j < this.BLOCK_LENGTH; j++){
				BLOCK_MATRIX[j][i] = secret[i*LINE_LENGTH + j];
			}
		}
		
	}
	
	

}
