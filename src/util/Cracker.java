package util;

public class Cracker {
	
	private BlockMatrix bm;
	private boolean cracked;
	private final String SECRET;
	private String plain;
		
	public Cracker(String secret){
		
		this.SECRET = secret;
		this.cracked = false;
				
	}
	
	public boolean isCracked(){
		
		return cracked;
	}
	
	public String getCrackedSecret(){
		
		return this.plain;
	}
	
	
	public boolean crackByKnownWord(String knownWord){
		
		// Wir testen eine Block-Länge von 3 bis 20
		for(int k = 2; k <= 20 ; k++){
			
			Transposition t = new Transposition(k);
			
			this.bm = new BlockMatrix(this.SECRET.toCharArray(), t);
			BlockMatrix known = new BlockMatrix(knownWord.toCharArray(), t);
			
			for(int i = 0; i < bm.getLineLength()-known.getLineLength(); i++){
				
				for(int j = 0 ; j < known.getLineLength() ; j++){
					
					boolean found = false;
					for(int l = 0 ; l < k ; l++){
						if(bm.getArray()[j][l] == known.getArray()[i+j][l]) found = true;
					}
					
				}
				
			}
			
		}
		
		
		return true;
		
	}

}
