package jp.gr.java_conf.assout;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class GeneralTest {

	public static class FailOnBeforeTest {
		@Before
		public void before() {
			System.out.println("before");
			throw new RuntimeException("hoge");
		}

		@After
		public void after() {
			System.out.println("after");
		}

		@Test
		public void testName() {

		}
	}
}
