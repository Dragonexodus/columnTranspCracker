package util;

import static org.junit.Assert.*;

import org.junit.Test;

public class SecretFormatterTest {

	
	
	@Test
	public void testGetSecret() {
		
		Transposition t = new Transposition("01234");
		BlockMatrix bm = new BlockMatrix(new String("Hallo Welt").toCharArray(),t);
		bm.transpose();
		SecretFormatter sf = new SecretFormatter(bm, 5, 10);
		
		assertEquals("H aWl ellot",sf.getSecret());
		
	}

}
