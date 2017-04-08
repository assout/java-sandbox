package jp.gr.java_conf.assout.java;

import java.util.function.Consumer;

import org.junit.Test;

public class LambdaExpressionsTest {
	@Test
	public void testName() {
		// old style
		{
			Consumer<String> c = new Consumer<String>() {
				@Override
				public void accept(String str) {
					System.out.println(str);
				}
			};
			c.accept("hoge");
		}

		// new style
		{
			Consumer<String> c = (str) -> {
				System.out.println(str);
			};
			c.accept("fuga");
		}
	}
}
