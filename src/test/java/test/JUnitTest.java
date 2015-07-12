package test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class JUnitTest {
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
		Timeout timeout = new Timeout(1000);
		public void testTimeout() {
			
		}
	}
}
