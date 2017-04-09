package jp.gr.java_conf.assout.java;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import org.junit.Test;

public class LambdaExpressionsTest {
	@Test
	public void test() {
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

	@Test
	public void testExecuterService() throws InterruptedException, ExecutionException {
		ExecutorService service = Executors.newCachedThreadPool();
		Future<String> submit = service.submit(() -> {
			String threadName = Thread.currentThread().getName();
			System.out.println("Hello " + threadName);
			return "hoge";
		});
		assertThat(submit.get(), is("hoge"));
	}

	@Test
	public void testComparator() throws InterruptedException, ExecutionException {
		class Developer {
			public Developer(String name) {
				this.name = name;
			}

			private final String name;
		}

		List<Developer> devels = Arrays.asList(new Developer("hoge"), new Developer("fuga"));

		// 簡易版
		devels.sort((Developer o1, Developer o2) -> o1.name.compareTo(o2.name));

		// ちゃんと定義した場合
		devels.sort((Developer o1, Developer o2) -> {
			return o1.name.compareTo(o2.name);
		});

		System.out.println(devels);
	}
}
