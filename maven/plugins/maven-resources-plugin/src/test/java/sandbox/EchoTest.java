package sandbox;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class EchoTest {
	@Test
	public void testName() throws Exception {
		URL resource = this.getClass().getResource("test.txt");
		List<String> readAllLines = Files.readAllLines(Paths.get(resource.toURI()));
		System.out.println(readAllLines);
	}
}
