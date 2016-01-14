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
		int width = this.columnWidth;
		int columns = this.columnsPerLine;
		for(int i = 0; i < blockMatrix.getBlockLength(); i++){ // Besser mit For each
			
			for(int j = 0; j < blockMatrix.getLineLength(); j++){
				
				formated += (blockMatrix.getArray()[j][i]);
				
				if((width -= 1) == 0){
					width = this.columnWidth;
					formated += " ";
				}	
				
				if((columns -= 1) == 0){
					columns = this.columnsPerLine;
					formated += "\n";
				}
			}
		}
		
		return formated;
		
	}
	
	
	public String getFormatedSecret(){
		
		return this.formatedSecret;
		
	}

}
