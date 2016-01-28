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
	
	public boolean decodeToFile(){
		
		String secret = in.readText();
		secret = secret.replaceAll("\\s", "");
		
		BlockMatrix bm = new BlockMatrix(secret.toCharArray(), this.TRANSPOSITION, false);
		
		// Transponieren der Block-Matrix nach vorgegebener Transposition
		bm.transpose();		
			
		String plainText = "";
		System.out.print("Start decoding...");
		// Zeilenweises Auslesen der Block-Matrix
		for(int rows = 0; rows < bm.getLineLength() ; rows ++ ){
			
			for(int columns = 0; columns < bm.getBlockLength(); columns++){
				
				plainText += bm.getArray()[rows][columns];
				
			}
			
		}
		System.out.println("\t\t\t\t\t\t\t\tdone");

		return out.writeText(plainText);	
		
	}
	
	private void createFiles(){
		
		this.in = new TextFileHandler(this.SOURCE_FILE_NAME);
		this.out = new TextFileHandler(this.DEST_FILE_NAME);		
		
	}

}
