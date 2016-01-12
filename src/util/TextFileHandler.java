package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileHandler {
	
	private final File TEXT_FILE;
	
	public TextFileHandler(String path){
		
		this.TEXT_FILE = new File(path);
		
	}
	
	public String readText(){
		
		BufferedReader reader;
		String content = "";
		try{
			reader = new BufferedReader(new FileReader(this.TEXT_FILE));
			while(true) content += reader.readLine();
		}
		catch(FileNotFoundException fnfe){
			System.out.println("Datei nicht gefunden!");
		}
		
		catch(IOException ioe){
			
		}
		
		System.out.println(content);
		return content;
		
	}
	
	public void writeText(String text){
		
		BufferedWriter writer;
		
		try{
			writer = new BufferedWriter(new FileWriter(this.TEXT_FILE));
			writer.write(text);
		}
		catch(FileNotFoundException fnfe){
			
			System.out.println("Datei nicht gefunden!");
		}
		catch(IOException ioe){}
		
	}

}
