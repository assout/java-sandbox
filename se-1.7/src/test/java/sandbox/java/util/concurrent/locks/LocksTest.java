package sandbox.java.util.concurrent.locks;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.junit.Test;

public class LocksTest {

	private static final Logger LOGGER = Logger.getLogger(LocksTest.class);

	@Test
	public void conditionTest() throws InterruptedException {
		final BoundedBuffer buffer = new BoundedBuffer();
		Thread t1 = new Thread() {
			@Override
			public void run() {
				try {
					buffer.put("dummy");
					LOGGER.info("put done");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread t2 = new Thread() {
			@Override
			public void run() {
				try {
					buffer.take();
					LOGGER.info("take done");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		t1.start();
		t2.start();
		Thread.sleep(1000000);
	}

	@Test
	public void testCyclicBarrier() throws Exception {
		final CyclicBarrier barrier = new CyclicBarrier(3);
		ExecutorService service = Executors.newCachedThreadPool();
		for (int i = 0; i < 6; i++) {
			service.submit(new Callable<Void>() {
				public Void call() throws Exception {
					barrier.await();
					LOGGER.info(Thread.currentThread());
					Thread.sleep(3000);
					return null;
				}
			});
			Thread.sleep(1000);
		}
		service.shutdown();
	}

	@Test
	public void testCyclicBarrierWithBarrierAction() throws Exception {
		final CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
			public void run() {
				LOGGER.info("barrier aciton! " + Thread.currentThread());
			}
		});
		ExecutorService service = Executors.newCachedThreadPool();
		for (int i = 0; i < 6; i++) {
			service.submit(new Callable<Void>() {
				public Void call() throws Exception {
					barrier.await();
					LOGGER.info(Thread.currentThread());
					Thread.sleep(3000);
					return null;
				}
			});
			Thread.sleep(1000);
		}
		service.shutdown();
	}

	@Test
	public void testReentrantLock() throws Exception {
		final ReentrantLock lock = new ReentrantLock();
		lock.lock(); // block until condition holds
		lock.lock(); // block until condition holds
		Thread.sleep(Long.MAX_VALUE);
	}

	@Test
	public void testReentrantLockOtherThread() throws Exception {
		final ReentrantLock lock = new ReentrantLock();
		new Thread() {
			public void run() {
				lock.lock();
				try {
					Thread.sleep(Long.MAX_VALUE);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
		Thread.sleep(1000L);
		lock.lock(); // block until condition holds
	}
}

class BoundedBuffer {
	final Lock lock = new ReentrantLock();
	final Condition notFull = lock.newCondition();
	final Condition notEmpty = lock.newCondition();

	final Object[] items = new Object[100];
	int putptr, takeptr, count;

	public void put(Object x) throws InterruptedException {
		lock.lock();
		try {
			while (count == items.length)
				notFull.await();
			items[putptr] = x;
			if (++putptr == items.length)
				putptr = 0;
			++count;
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	public Object take() throws InterruptedException {
		lock.lock();
		try {
			while (count == 0)
				notEmpty.await();
			Object x = items[takeptr];
			if (++takeptr == items.length)
				takeptr = 0;
			--count;
			notFull.signal();
			return x;
		} finally {
			lock.unlock();
		}
	}
}