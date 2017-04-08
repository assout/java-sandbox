package jp.gr.java_conf.assout.rules;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.ExpectedException;
import org.junit.rules.RuleChain;
import org.junit.rules.Stopwatch;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class RuleTest {

	private static Logger logger = Logger.getLogger(RuleTest.class.getName());

	public static class DisableOnDebugTest {
		@Rule
		public TestRule timeout = new DisableOnDebug(Timeout.seconds(1)); // 1秒以上かかったら失敗とみなす

		@Test
		public void testDisableOnDebug() throws Exception {
			Thread.sleep(2000l);
			System.out.println("test");
		}

	}

	public static class StopWathcTest {
		@Rule
		public Stopwatch stopwatch = new Stopwatch() {
			@Override
			protected void succeeded(long nanos, Description description) {
				logger.info(String.format("テストの実行に%,dナノ秒かかった", nanos));
			}
		};

		@Test
		public void testStopWatch() throws Exception {
			System.out.println("test");
		}
	}

	public static class ExpectedExceptionTest {
		@Rule
		public ExpectedException thrown = ExpectedException.none();

		@Test
		public void testThrowException() {
			thrown.expect(NumberFormatException.class);
			thrown.expectMessage("test");

			throw new NumberFormatException("test");
		}

		@Test
		public void testThrowExceptionNotThrown() {
			thrown.expect(NumberFormatException.class);
			thrown.expectMessage("test");

			// nop
		}

	}

	public static class TestWatcherTest {
		@Rule
		public TestWatcher w = new TestWatcher() {
			@Override
			protected void starting(Description description) {
				logger.info("start: " + description.getDisplayName());
			}

			protected void finished(Description description) {
				logger.info("finished: " + description.getDisplayName());
			};
		};

		@Test
		public void test() {
			System.out.println("hoge");
		}

		@Test
		public void test2() {
			System.out.println("hoge2");
		}
	}

	public static class ExpectedExceptionWithLoggingTest {
		private ExpectedException thrown = ExpectedException.none();
		private TestWatcher watcher = new TestWatcher() {
			@Override
			protected void failed(Throwable e, Description description) {
				System.out.println("threw throable in test case. throable :");
				e.printStackTrace();
			}
		};
		@Rule
		public TestRule chain = RuleChain.outerRule(thrown).around(watcher);

		@Test
		public void testThrowExpectedException() {
			thrown.expect(NumberFormatException.class);
			thrown.expectMessage("test");
			throw new NumberFormatException("test");
		}

		@Test
		public void testNotThrowExpectedException() {
			thrown.expect(NumberFormatException.class);
			thrown.expectMessage("test");
			// nop
		}

		@Test
		public void testThrowNotExceptedException() {
			throw new NumberFormatException("test");
		}
	}

	public static class TimeoutTest {

		@Rule
		public Timeout timeout = new Timeout(100L, TimeUnit.MILLISECONDS);

		@Test
		public void testTimeout() throws InterruptedException {
			Thread.sleep(1000L);
		}
	}

}