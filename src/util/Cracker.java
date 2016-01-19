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
			
			// Diese dient zum Abschätzen der Transposition
			Transposition guessed = new Transposition(0);
			
						
			this.bm = new BlockMatrix(this.SECRET.toCharArray(), t);
			BlockMatrix known = new BlockMatrix(knownWord.toCharArray(), t);
			
			// Es wird (im schlechtesten Fall) die ganze Block-Matrix durchsucht
			for(int i = 0; i < bm.getLineLength()-known.getLineLength(); i++){
				
				// Es wird jedes Zeichen von known Word gesucht
				for(int l = 0 ; l < k ; l++){
					
					boolean found = true;
					
					// Für die Zeile in der Geheimtext Block-Matrix wird jede Stelle auf Vorkommnis
					// eines Zeichens aus known Word gesucht
					for(int m = 0; m < k; m++){
						
						// In Abhängigkeit der Blocklänge entsteht ein Umbruch in der BM known word
						// Damit ergibt sich eine Zeilenanzahl
						for(int j = 0 ; j < known.getLineLength() ; j++){
							
							if(bm.getArray()[i+j][m] == known.getArray()[j][l]) found &= true;
							else{
								
								found &= false;
								break;
							}
							
						}	
						
						if(found){
							
							guessed.getTransposition().add(m);
							
						}
						
					}
					
				}
				
			}
			
		}		
		
		return true;
		
	}

}
