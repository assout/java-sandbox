package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class JUnitTest {

	private static final Logger LOGGER = Logger.getLogger(JUnitTest.class);

	public static class ExceptedExceptionTest {
		@Rule
		public ExpectedException thrown = ExpectedException.none();

		@Test
		public void testThrown() throws Exception {
			/* Setup */
			thrown.expect(IllegalArgumentException.class);

			/* Exercise */
			throw new IllegalArgumentException("test");

			/* Verify */
		}

		@Test
		public void testNotThrown() throws Exception {
			/* Setup */
			thrown.expect(IllegalArgumentException.class);

			/* Exercise */

			/* Verify */
		}
	}

	public static class TimeoutTest {

		@Rule
		Timeout timeout = new Timeout(10L, TimeUnit.MINUTES);

		public void testTimeout() {

		}
	}

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
