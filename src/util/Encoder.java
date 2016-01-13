/**
 * 
 */
package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

//TODO Complete this class
public class Encoder {

		
	private final String SOURCE_FILE_NAME;
	private final String DEST_FILE_NAME;
	private final Transposition TRANSPOSITION;
	
	/**
	 * Kodiert eine Datei mit der gegebenen Transposition um so ein Beispiel zu erhalten
	 * Vorgaben der Aufgabe sind: 10 Blöcke je Zeile....
	 * @param transposition
	 * @param sourceFileName
	 */
	public Encoder(String transposition, String sourceFileName){			
		if(sourceFileName != null){
			this.SOURCE_FILE_NAME = sourceFileName;
			if(sourceFileName.contains(".txt")){
				this.DEST_FILE_NAME = sourceFileName.replace(".txt", "encode.txt");
			}else{
				this.DEST_FILE_NAME = sourceFileName+"encode.txt";
			}
		}else{
			this.SOURCE_FILE_NAME = "";
			this.DEST_FILE_NAME  = "";
		}	
		TRANSPOSITION = new Transposition(transposition);
	}
	
	public int encodeFile(){
		int returnCode = 0;
		
		BufferedReader reader = null;
		BufferedWriter bufferedWriter = null;
		try {
			 reader = new BufferedReader(new FileReader(SOURCE_FILE_NAME));
			 bufferedWriter = new BufferedWriter(new FileWriter(DEST_FILE_NAME));
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound: Source");
			returnCode = -1;
		} catch (IOException e) {
			System.out.println("FileNotFound: Desti");
			returnCode = -1;
		}
		
		if((reader != null) && bufferedWriter != null){
			String text = "";
			
			try {
				text = reader.readLine();
				text = text.replaceAll(("[^0-9a-zA-Z]"),"");
				
			} catch (IOException e1) {
				System.out.println("FileReadLineError");
				returnCode = -1;
			};
			
			BlockMatrix colum = new BlockMatrix(TRANSPOSITION.getBlockLength()
					,text.toCharArray(),TRANSPOSITION);
			
			char[][] matrix = colum.getArray();
			
			try {
				for (char[] cs : matrix) {
					for (char c : cs) {
						bufferedWriter.write(c);
					}
				}		
			} catch (IOException e1) {
				System.out.println("Writing file failed");
				returnCode = -1;
			}
			
			System.out.println(Arrays.deepToString(colum.getArray()));			
		}
		
		try {
			bufferedWriter.close();
			reader.close();
		} catch (IOException e1) {
			System.out.println("Closing files failed");
			returnCode = -1;
		}
		
		return returnCode;
				
	}
}
