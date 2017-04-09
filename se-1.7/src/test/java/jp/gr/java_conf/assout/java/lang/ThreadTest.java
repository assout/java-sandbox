package jp.gr.java_conf.assout.java.lang;

import static org.junit.Assert.*;

import java.lang.Exception;
import java.lang.InterruptedException;
import java.lang.Override;
import java.lang.Runnable;
import java.lang.RuntimeException;
import java.lang.System;
import java.lang.Thread;
import java.lang.Throwable;
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
	public void testDefaultUncaughtException() throws Exception {
		Thread t1 = new Thread() {
			@Override
			public void run() {
				throw new RuntimeException("my error");
			}
		};
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread thread, Throwable throwable) {
				LOGGER.error("#1: error", throwable);
				LOGGER.info("#2: " + thread.getId());
				LOGGER.info("#3: " + Thread.currentThread().getId());
				fail();
			}
		});
		LOGGER.info("#4 " + Thread.currentThread().getId());
		t1.start();
		t1.join();
	}

	@Test
	public void testUncaughtException() throws Exception {
		Thread t1 = new Thread() {
			@Override
			public void run() {
				throw new RuntimeException("my error");
			}
		};
		t1.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread thread, Throwable throwable) {
				LOGGER.error("#1: error", throwable);
				LOGGER.info("#2: " + thread.getId());
				LOGGER.info("#3: " + Thread.currentThread().getId());
				fail();
			}
		});
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread thread, Throwable throwable) {
				LOGGER.info("#5: " + thread.getId());
			}
		});
		LOGGER.info("#4 " + Thread.currentThread().getId());
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
