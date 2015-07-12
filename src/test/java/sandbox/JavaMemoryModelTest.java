package sandbox;

import static org.junit.Assert.*;

import org.junit.Test;

public class JavaMemoryModelTest {
	static int x = 0;
	static int y = 0;
	static int a = 0;
	static int b = 0;

	/**
	 * http://d.hatena.ne.jp/j5ik2o/touch/20110225/1298610671
	 */
	@Test
	public void testReOdering() throws Exception {

		Thread aThread = new Thread(new Runnable() { // スレッドA
					@Override
					public void run() {
						a = 1;
						x = b;
					}
				});
		Thread bThread = new Thread(new Runnable() { // スレッドB
					@Override
					public void run() {
						b = 1;
						y = a;
					}
				});
		aThread.start();
		bThread.start();
		try {
			aThread.join();
			bThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(" ( " + x + " , " + y + " )");
	}
}
