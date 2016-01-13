package util;

import static org.junit.Assert.*;

import org.junit.Test;

public class EncoderTest {

	@Test
	public void test1encodeFile() {
		Encoder test = new Encoder("123", "ressource/testFile.txt");
		test.encodeFile();
	}
	@Test
	public void test2encodeFile() {
		Encoder test = new Encoder("", "ressource/testFile.txt");
		test.encodeFile();
	}
	@Test
	public void test3encodeFile() {
		Encoder test = new Encoder("123", "");
		test.encodeFile();
	}
	@Test
	public void test4encodeFile() {
		Encoder test = new Encoder("", "");
		test.encodeFile();
	}
	
	@Test
	public void test5encodeFile() {
		Encoder test = new Encoder(null, null);
		test.encodeFile();
	}
}
