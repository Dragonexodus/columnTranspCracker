package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileHandler {
	
	private final File TEXT_FILE;
	
	public TextFileHandler(String path){
		
		this.TEXT_FILE = new File(path);
		if(!this.TEXT_FILE.exists()){
			
			try {
				this.TEXT_FILE.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
	}
	
	public String readText(){
		
		BufferedReader reader;
		String content = "";
		try{
			reader = new BufferedReader(new FileReader(this.TEXT_FILE));
			String tmp;
			while((tmp = reader.readLine()) != null) content += tmp;
			reader.close();
		}
		catch(FileNotFoundException fnfe){
			System.out.println("Datei nicht gefunden!");
		}
		
		catch(EOFException eofe){
			System.out.println("Datei gelesen");
		}
		
		catch(IOException ioe){
			
		}
		
		//System.out.println(content);
		return content;
		
	}
	
	public boolean writeText(String text){
		
		BufferedWriter writer;
		
		try{
			writer = new BufferedWriter(new FileWriter(this.TEXT_FILE));
			writer.write(text);
			writer.close();
		}
		
		catch(FileNotFoundException fnfe){
			
			System.out.println("Datei nicht gefunden!");
			return false;			
		}
		catch(IOException ioe){
			return false;
		}
		
		return true;
		
	}

}
