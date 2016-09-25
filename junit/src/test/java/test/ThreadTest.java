package test;

import static org.junit.Assert.*;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.atomic.AtomicReference;

import junit.framework.AssertionFailedError;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

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

	@Test
	public void testAssertionOnOtherThreadWithUncaughtException() throws Throwable {
		final AtomicReference<Throwable> t = new AtomicReference<Throwable>();
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
				t.set(paramThrowable);
			}
		});

		Thread t1 = new Thread(new Runnable() {
			public void run() {
				LOGGER.info("test");
				fail("faiiiiil");
			}
		});

		t1.start();
		t1.join();

		if (t.get() != null) {
			throw t.get();
		}
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
