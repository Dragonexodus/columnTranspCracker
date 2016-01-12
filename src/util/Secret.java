package util;

public class Secret {
	
	private final Transposition T;
	private final char[] TEXT;
	private String secret;
	
	public Secret(Transposition t, String text){
		
		this.T= t;
		this.TEXT = text.toCharArray();
		
		
	}
	
	public String getSecret(){
		
		return this.secret;
		
	}
	
	private void encode(){
		
		
		
	}

}
