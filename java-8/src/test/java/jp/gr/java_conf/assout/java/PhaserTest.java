package jp.gr.java_conf.assout.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Test;

public class PhaserTest {

	private ExecutorService es = Executors.newCachedThreadPool();

	private static final Logger LOG = Logger.getLogger(PhaserTest.class);

	@Test
	public void testPhase() throws Exception {
		Phaser p = new Phaser(2);
		LOG.info(p);
		p.arrive();
		LOG.info(p);
		p.arrive();
		LOG.info(p);
		p.arrive();
		LOG.info(p);
		p.arrive();
		LOG.info(p);
		p.arrive();
		LOG.info(p);
		p.arrive();
		LOG.info(p);
	}

	@Test
	public void testRegister() throws Exception {
		Phaser p = new Phaser(2);
		LOG.info(p);
		p.arrive();
		LOG.info(p);
		p.arrive();
		p.register();
		LOG.info(p);
	}

	@Test
	public void testArriveAndAwaitAdbance() throws Exception {
		Phaser p = new Phaser(2);
		LOG.info(p);

		es.submit(() -> {
			try {
				TimeUnit.MILLISECONDS.sleep(10L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			LOG.info(p);
			p.arrive();
		});
		p.arriveAndAwaitAdvance();

		LOG.info(p);
	}

	@Test
	public void testAwaitAdbance() throws Exception {
		Phaser p = new Phaser(2);

		es.submit(() -> {
			try {
				TimeUnit.MILLISECONDS.sleep(10L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			LOG.info("foo");
			p.arrive();
		});
		p.arriveAndAwaitAdvance();

		LOG.info("foo");
	}

	@Test
	public void testAwaitAdbance2() throws Exception {
		Phaser p = new Phaser(2);

		Future<?> s = es.submit(() -> {
			LOG.info(p);
			p.arriveAndAwaitAdvance();

			p.awaitAdvance(1);

			LOG.info(p);
			p.arriveAndAwaitAdvance();

			p.awaitAdvance(2);
		});

		LOG.info(p);
		p.arriveAndAwaitAdvance();

		LOG.info(p);
		p.awaitAdvance(1);

		LOG.info(p);
		p.arriveAndAwaitAdvance();

		p.awaitAdvance(2);
		s.get();
	}
}
