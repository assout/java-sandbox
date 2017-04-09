package jp.gr.java_conf.assout;

import static org.junit.Assert.fail;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class CustomAssertTest {

	public static class AssertTimeout {

		@Test
		public void testName() {
			Future<?> submit = Executors.newCachedThreadPool().submit(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(10L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			assertTimeout(submit, 5L);

		}

		private void assertTimeout(Future<?> future, long timeout) {
			try {
				future.get(timeout, TimeUnit.MILLISECONDS);
				fail("future was not timeouted. future=" + future);
			} catch (TimeoutException e) {
				// ok
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

	}

}
