package util;

public class Decoder {
	
	private final String SOURCE_FILE_NAME;
    private final String DEST_FILE_NAME;
    private final Transposition TRANSPOSITION;
    
    private TextFileHandler in,out;
	
	public Decoder(String permutation, String secretFile){
		
		this.TRANSPOSITION = new Transposition(permutation);
		this.SOURCE_FILE_NAME = secretFile;
		this.DEST_FILE_NAME = secretFile.replace(".txt","Decoded.txt");
		
		
		
		this.createFiles();
		
	}
	
	public Decoder(Transposition t, String secretFile){
		
		this.TRANSPOSITION = t;
		this.SOURCE_FILE_NAME = secretFile;
		this.DEST_FILE_NAME = secretFile.replace(".txt", "Decoded.txt");
		
		this.createFiles();
	}
	
	public boolean decode(){
		
		BlockMatrix bm = new BlockMatrix(in.readText().toCharArray(), this.TRANSPOSITION, false);
		
		// Transponieren der Block-Matrix nach vorgegebener Transposition
		bm.transpose();		
			
		String plainText = "";
		
		// Zeilenweises Auslesen der Block-Matrix
		for(int rows = 0; rows < bm.getLineLength() ; rows ++ ){
			
			for(int columns = 0; columns < bm.getBlockLength(); columns++){
				
				plainText += bm.getArray()[rows][columns];
				
			}
			
		}
		
		return out.writeText(plainText);	
		
	}
	
	private void createFiles(){
		
		this.in = new TextFileHandler(this.SOURCE_FILE_NAME);
		this.out = new TextFileHandler(this.DEST_FILE_NAME);		
		
	}

}
