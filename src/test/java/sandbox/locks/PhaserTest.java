package sandbox.locks;

import static org.junit.Assert.*;

import java.util.List;
import java.util.concurrent.Phaser;

import org.junit.Test;

/**
 * http://examples.javacodegeeks.com/core-java/util/concurrent/phaser/java-util-concurrent-phaser-example/
 */
public class PhaserTest {
	@Test
	public void testPhaser() throws Exception {
		Phaser phaser = new Phaser();
		phaser.register();//register self... phaser waiting for 1 party (thread)
		int phasecount = phaser.getPhase();
		System.out.println("Phasecount is " + phasecount);
		testPhaser(phaser, 2000);//phaser waiting for 2 parties
		testPhaser(phaser, 4000);//phaser waiting for 3 parties
		testPhaser(phaser, 6000);//phaser waiting for 4 parties
		//now that all threads are initiated, we will de-register main thread 
		//so that the barrier condition of 3 thread arrival is meet.
		phaser.arriveAndDeregister();
		Thread.sleep(10000);
		phasecount = phaser.getPhase();
		System.out.println("Phasecount is " + phasecount);
	}

	private void testPhaser(final Phaser phaser, final int sleepTime) {
		phaser.register();
		new Thread() {
			@Override
			public void run() {
				try {
					System.out.println(Thread.currentThread().getName() + " arrived");
					phaser.arriveAndAwaitAdvance();//threads register arrival to the phaser.
					Thread.sleep(sleepTime);
				}

				catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " after passing barrier");
			}
		}.start();
	}

	@Test
	public void testPhase2() throws Exception {
		Phaser phaser = new Phaser();

	}
}
