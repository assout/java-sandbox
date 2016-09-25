package sandbox;

import static org.junit.Assert.*;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class ThreadTest {

	private static final Logger LOGGER = Logger.getLogger(ThreadTest.class);

	@Rule
	public Timeout timeout = new Timeout(5, TimeUnit.SECONDS);

	@Test
	public void testUncaughtExceptionHanler() throws Exception {
		Thread t1 = new Thread() {
			@Override
			public void run() {
				throw new RuntimeException("my error");
			}
		};
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread thread, Throwable throwable) {
				LOGGER.error("error", throwable);
				LOGGER.info(thread.getId());
				LOGGER.info(Thread.currentThread().getId());
				fail();
			}
		});
		LOGGER.info(Thread.currentThread().getId());
		t1.start();
		t1.join();
	}


	boolean running = true;
	/**
	 * http://stackoverflow.com/questions/5816790/the-code-example-which-can-
	 * prove-volatile-declare-should-be-used
	 */
	@Test
	public void testVolatile() throws InterruptedException {
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				int counter = 0;
				while (running) {
					counter++;
				}
				System.out.println("Thread 1 finished. Counted up to " + counter);
			}
		});
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				// Sleep for a bit so that thread 1 has a chance to start
				try {
					Thread.sleep(100);
				} catch (InterruptedException ignored) {
				}
				System.out.println("Thread 2 finishing");
				running = false;
			}
		});
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}
}
