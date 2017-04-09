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

			final String name;

			public String getName() {
				return name;
			}

		}

		List<Developer> devels = Arrays.asList(new Developer("hoge"), new Developer("fuga"));

		devels.sort((Developer o1, Developer o2) -> o1.getName().compareTo(o2.getName()));

		System.out.println(devels);
	}
}
