package util;
import java.util.ArrayList;
import java.util.List;

public class Transposition {
	
	private final List<Integer> PERMUTATION;
	private final int BLOCK_LENGTH;
	
	public Transposition(String permutation){
		if(permutation != null){
			permutation.split("");
			this.BLOCK_LENGTH = permutation.length();
			this.PERMUTATION = new ArrayList<Integer>(this.BLOCK_LENGTH);
		}else{
			this.BLOCK_LENGTH = 0;
			this.PERMUTATION = null;
		}
	}

	public List<Integer> getTransposition(){
		return this.PERMUTATION;
	}
	
	public int getBlockLength(){
		return this.BLOCK_LENGTH;
	}
	
}
