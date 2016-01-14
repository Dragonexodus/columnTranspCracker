package util;

/**
 * Diese Klasse formatiert ein durch die Klasse Encoder verschlüsseltes Geheimnis 
 * für eine Ausgabe in eine Textdatei.
 * @author frank
 * 
 */

public class SecretFormatter {
	
	private final int columnWidth, columnsPerLine;
	private final BlockMatrix blockMatrix;
	private final String formatedSecret; 
	
	public SecretFormatter(BlockMatrix blockMatrix, int columnWidth, int columnsPerLine){
		
		this.blockMatrix = blockMatrix;
		this.columnWidth = columnWidth;
		this.columnsPerLine = columnsPerLine;
		this.formatedSecret = this.format();
	}
	

	/**
	 * 
	 * @param blockMatrix
	 * @param textColumnWidth
	 */
	private String format(){
		
		String formated = "";
		
		int index = 0, lineFeed = this.columnWidth * this.columnsPerLine;
		for(int i = 0; i < blockMatrix.getBlockLength(); i++){ // Besser mit For each
			
			for(int j = 0; j < blockMatrix.getLineLength(); j++){
				
				// Prüfe, ob line feed oder space notwendig 
				if(index > 0 && index % lineFeed == 0) formated += "\n";
				else if(index > 0 && index % this.columnWidth == 0) formated += " ";
				
				formated += (blockMatrix.getArray()[j][i]);
				
				index++;
											
			}
		}
		
		return formated;
		
	}
	
	
	public String getSecret(){
		
		return this.formatedSecret;
		
	}

}
