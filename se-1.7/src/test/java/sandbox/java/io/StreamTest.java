package sandbox.java.io;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

public class StreamTest {
	@Test
	public void testName() throws Exception {
		try (ByteArrayOutputStream bout = new ByteArrayOutputStream();) {
			bout.close();
		}
	}
}
