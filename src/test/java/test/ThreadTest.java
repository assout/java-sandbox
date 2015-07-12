package test;

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.AssertionFailedError;

public class ThreadTest {

	private static final Logger LOGGER = Logger.getLogger(ThreadTest.class);
	private final AtomicReference<Throwable> threadFailure = new AtomicReference<Throwable>(null);

	@After
	public void tearDown() throws Exception {
		Throwable t = threadFailure.getAndSet(null);
		if (t != null) {
			if (t instanceof Error)
				throw (Error) t;
			else if (t instanceof RuntimeException)
				throw (RuntimeException) t;
			else if (t instanceof Exception)
				throw (Exception) t;
			else {
				AssertionFailedError afe = new AssertionFailedError(t.toString());
				afe.initCause(t);
				throw afe;
			}
		}

		if (Thread.interrupted())
			throw new AssertionFailedError("interrupt status set in main thread");
	}

	@Test
	public void testAssertionOnOtherThread() throws Exception {
		Thread t1 = new Thread() {
			public void run() {
				try {
					LOGGER.info("run");
					fail("faaaaail");
				} catch (Throwable e) {
					threadFailure.set(e);
				}
			}
		};
		t1.start();
		t1.join();
	}

	@Test
	public void testAssertionOnOtherThreadWithUtil() throws Exception {
		Thread t1 = new Thread(new CheckedRunnable() {
			@Override
			protected void realRun() throws Throwable {
				LOGGER.info("run");
				fail("faaaaail");
			}
		});
		t1.start();
		t1.join();
	}

	public abstract class CheckedRunnable implements Runnable {
		protected abstract void realRun() throws Throwable;

		public final void run() {
			try {
				realRun();
			} catch (Throwable fail) {
				threadFailure.set(fail);
			}
		}
	}
}
