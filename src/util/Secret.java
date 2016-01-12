package util;

public class Secret {
	
	private final Transposition T;
	private final char[] TEXT;
	private String secret;
	
	public Secret(Transposition t, String text){
		
		this.T= t;
		this.TEXT = text.toCharArray();
		
		
	}
	
	private void code(BlockMatrix blockMatrix, int textColumnWidth){
		
		String encoded = "";
		int column;
		for(int i = 0; i < blockMatrix.getBlockLength(); i++){
			
			for(int j = 0; j < blockMatrix.getLineLength(); j++){
				
				encoded += (blockMatrix.getArray()[j][i]);
				
			}
		}
		
	}

}
