package sandbox.java.util.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinPool.ManagedBlocker;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;


public class ManagedBlockerTest {
	// refs: <http://www.concretepage.com/java/jdk7/example-of-managedblocker-in-java>
	@Test
	public void testQueueBlocker() throws Exception {
		BlockingQueue<String> bq = new ArrayBlockingQueue<String>(2);
		bq.put("A");
		bq.put("B");
		QueueManagedBlocker<String> blocker = new QueueManagedBlocker<String>(bq);
		ForkJoinPool.managedBlock(blocker);
		System.out.println(blocker.getValue());
	}

	@Test
	public void testReentrantLocker() throws Exception {
		Locker locker = new Locker(new ReentrantLock());
		ForkJoinPool.managedBlock(locker);
	}
}

class QueueManagedBlocker<T> implements ManagedBlocker {
	  final BlockingQueue<T> queue;
	  volatile T value = null;
	  QueueManagedBlocker(BlockingQueue<T> queue) {
		this.queue = queue;
	  }
	  public boolean block() throws InterruptedException {
		if (value == null)
		   value = queue.take();
		return true;
	  }
	  public boolean isReleasable() {
		return value != null || (value = queue.poll()) != null;
	  }
	  public T getValue() {
		return value;
	  }
}

class Locker implements ManagedBlocker {
	  final ReentrantLock rtlock;
	  boolean isLocked = false;
	  Locker(ReentrantLock rtlock) {
		this.rtlock = rtlock;
	  }
	  public boolean block() {
		if (!isLocked){
		   rtlock.lock();
		}
		return true;
	  }
	  public boolean isReleasable() {
		return isLocked || (isLocked = rtlock.tryLock());
	  }
}