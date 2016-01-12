/**
 * 
 */
package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 *
 */
public class Coder {

		
	final String FILE_NAME;
	final String TRANSPOSITION;
	public Coder(String transposition, String fileName){
		this.FILE_NAME = fileName;
		this.TRANSPOSITION = transposition;
		
	}
	
	public void doSth(String fileName){
		
		BufferedReader reader = null;
		try {
			 reader = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(reader != null){
			
			String text = null;
			try {
				text = reader.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			text = text.replace(" ", "");
			text = text.replaceAll(("[^0-9a-zA-Z]"),"");
			System.out.println(text);
			char[] test = text.toCharArray();
			System.out.println();
			
			BlockMatrix colum = new BlockMatrix(6,test,null);
			
			
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
	}
}
