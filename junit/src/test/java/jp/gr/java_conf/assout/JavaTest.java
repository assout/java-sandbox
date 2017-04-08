package jp.gr.java_conf.assout;

import org.apache.log4j.Logger;
import org.junit.Test;

public class JavaTest {
	public static class Inner {
		static Logger logger = Logger.getLogger(JavaTest.class);

		public static void tetete() {

		}

		@Test
		public void testLog() {
			logger.error("test");
			tetete();
		}

		@Test
		public static void newtest() {
			logger.error("test");
			tetete();
		}

		public static void oldtest() {
			newtest();
		}

		@Test
		public void testLogWithCause() {
			oldtest();
			Exception e = new Exception("testing exception!",
					new IllegalAccessException());
			logger.error("test", e);
		}

		@Test
		public void testLogOnlyCause() {
			Exception e = new Exception("testing exception!",
					new IllegalAccessException());
			logger.error(e);
		}
	}
}
