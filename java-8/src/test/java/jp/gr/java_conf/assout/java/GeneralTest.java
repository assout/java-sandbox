package jp.gr.java_conf.assout.java;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Test;

public class GeneralTest {

	private static final Logger LOGGER = Logger.getLogger(GeneralTest.class);
	private static final CountDownLatch shutdown = new CountDownLatch(1);

	@Test
	public void testName() throws Exception {
		assertTrue("E001 hoge".contains("E001"));
	}

	@Test
	public void testShutdownHookAndProcessBuilder() throws Exception {
		List<String> command = new ArrayList<>();
		command.add("java");
		command.add("-cp");
		String property = System.getProperty("java.class.path");
		command.add(property);
		String canonicalName = this.getClass().getCanonicalName();
		command.add(canonicalName);

		ProcessBuilder pb = new ProcessBuilder(command);

		pb.inheritIO();

		Process process = pb.start();

		LOGGER.info("proc start!");
		TimeUnit.SECONDS.sleep(5L);
		LOGGER.info("proc end!");

		LOGGER.info("destroy!");
		process.destroy();
		LOGGER.info("destroied!");

		int waitFor = process.waitFor();
		LOGGER.info(waitFor);
	}

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				LOGGER.error("shutdown hook start");
				shutdown.countDown();
				LOGGER.error("shutdown hook end");
			}
		});

		try {
			LOGGER.error("main start");
			shutdown.await();
		} catch (InterruptedException e) {
			LOGGER.error("interrupted. end");
		}
		LOGGER.error("main end");
	}
}
