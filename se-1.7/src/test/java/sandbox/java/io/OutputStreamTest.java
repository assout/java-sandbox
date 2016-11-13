package sandbox.java.io;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

public class OutputStreamTest {
	@Test
	public void testName() throws Exception {
		try (ByteArrayOutputStream bout = new ByteArrayOutputStream();) {
			bout.close();
		}
	}
}
