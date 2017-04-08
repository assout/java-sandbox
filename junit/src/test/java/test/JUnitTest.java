package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class JUnitTest {

	private static final Logger LOGGER = Logger.getLogger(JUnitTest.class);

	public static class BootProcessTest {

		@Test
		public void testLs() throws Exception {
			ProcessBuilder builder = new ProcessBuilder("ls");
			Process start = builder.directory(Paths.get("/home/oji/").toFile()).start();

			BufferedReader br = new BufferedReader(new InputStreamReader(start.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				LOGGER.info(line);
			}
			start.waitFor();
		}

		@Test
		public void testJava() throws Exception {
			ProcessBuilder builder = new ProcessBuilder("java", "test.Main");
			builder.redirectErrorStream(true);
			Path path = Paths.get(".", "target", "classes");
			LOGGER.info(path.toAbsolutePath());
			Process start = builder.directory(path.toFile()).start();

			BufferedReader br = new BufferedReader(new InputStreamReader(start.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				LOGGER.info(line);
			}
			start.waitFor();
		}
	}
}
