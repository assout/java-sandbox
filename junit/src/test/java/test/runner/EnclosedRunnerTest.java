package test.runner;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class EnclosedRunnerTest {

	@RunWith(Enclosed.class)
	public static class InnerClass {
		public static class InnerInnerClass {
			@Test
			public void test() throws Exception {
				System.out.println("test");
				this.test2();
			}

			@Test
			public void test2() throws Exception {
				System.out.println("test");

			}
		}
	}
}