package util;
import java.util.ArrayList;
import java.util.List;

public class Transposition {
	
	final List<Integer> PERMUTATION;
	final int BLOCK_LENGTH;
	
	public Transposition(String permutation){
		
		permutation.split("");
		this.BLOCK_LENGTH = permutation.length();
		PERMUTATION = new ArrayList<Integer>(BLOCK_LENGTH);
		
	}
	
	public List<Integer> getTransposition(){
		return this.PERMUTATION;
	}
	
	public int getBlockLength(){
		return this.BLOCK_LENGTH;
	}

}
