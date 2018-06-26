package jp.gr.java_conf.assout.java;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;
import org.junit.Test;

public class SemaphoreTest {

	private ExecutorService es = Executors.newCachedThreadPool();

	private static final Logger LOG = Logger.getLogger(SemaphoreTest.class);

	@Test
	public void testSemaphore() throws Exception {
		Semaphore s = new Semaphore(1, true);

		Future<Object> submit = es.submit(() -> {
			LOG.info(s);
			s.acquire();
			return null;
		});
		s.acquire();
		LOG.info(s);
		// s.release();

		submit.get();
	}

	@Test
	public void test2() throws Exception {
		Semaphore s = new Semaphore(0, true);

		{
			boolean tryAcquire = s.tryAcquire();
			System.err.println(tryAcquire);
		}

		s.release();

		{
			boolean tryAcquire = s.tryAcquire();
			System.err.println(tryAcquire);
		}
	}
}
