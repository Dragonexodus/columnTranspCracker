/**
 * 
 */
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.layout.ColumnConstraints;

/**
 * 
 *
 */
public class Coder {

		
	private final String SOURCE_FILE_NAME;
	private final String DEST_FILE_NAME;
	private final Transposition TRANSPOSITION;
	
	public Coder(String transposition, String sourceFileName){
		
		this.SOURCE_FILE_NAME = sourceFileName;
		this.DEST_FILE_NAME = "encode." + sourceFileName;

		TRANSPOSITION = new Transposition(transposition);
		
	}
	public Coder(String transposition, String sourceFileName, String destFileName){
		
		this.SOURCE_FILE_NAME = sourceFileName;
		this.DEST_FILE_NAME = destFileName;

		TRANSPOSITION = new Transposition(transposition);
		
	}
	
	public void encodeFile(){
		
		if(SOURCE_FILE_NAME.isEmpty()){
			return;
		}
		
		BufferedReader reader = null;
		try {
			 reader = new BufferedReader(new FileReader(SOURCE_FILE_NAME));
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
		}
		
		if(reader != null){
			String text = "";
			
			try {
				text = reader.readLine();
				text = text.replaceAll(("[^0-9a-zA-Z]"),"");
				
			} catch (IOException e1) {
				System.out.println("FileReadLineError");
			};
	
			System.out.println(text);
			char[] test = text.toCharArray();
			System.out.println();
			
			BlockMatrix colum = new BlockMatrix(6,test,null);
			
			char[][] matrix = colum.getArray();
			
			for (char[] cs : matrix) {
				for (char c : cs) {
					System.out.println(c);
				}
			}
						
			try {
				reader.close();
			} catch (IOException e) {
				System.out.println("ReaderClose");
			}
		}
				
	}
}
