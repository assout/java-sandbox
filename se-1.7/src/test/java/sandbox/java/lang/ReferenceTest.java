package sandbox.java.lang;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

import org.junit.Test;

public class ReferenceTest {

	/**
	 * http://www.ne.jp/asahi/hishidama/home/tech/java/weak.html
	 */
	@Test
	public void testWeakReference() throws Exception {
		//Integerオブジェクトの弱参照を保持する
		Integer integer = new Integer(123456);
		WeakReference<Integer> wr = new WeakReference<Integer>(integer);
		//WeakReferenceの中身を取得・表示
		Integer i = wr.get();
		System.out.println(i);
		System.out.println("↓");

		//強参照を全て無くす
		integer = null;
		i = null;
		System.gc();

		//GC実行後に中身を取得・表示
		i = wr.get();
		System.out.println(i);
	}

	/**
	 * http://www.ne.jp/asahi/hishidama/home/tech/java/weak.html
	 */
	@Test
	public void testWeakHashmap() throws Exception {
		Map<Integer, String> map = new WeakHashMap<Integer, String>();

		// キーを強参照で保持しつつ、マップに値をセット
		Integer[] force = new Integer[10];
		for (int i = 0; i < force.length; i++) {
			force[i] = new Integer(i); //強参照としてキーに当たる値を保持
			map.put(force[i], String.valueOf((char) ('A' + i)));
		}

		// マップの初期状態を表示
		System.out.println(map.toString());
		System.out.println("↓");

		force[1] = null; // 強参照を無くしてみる

		System.gc(); // GCを実行
		//Thread.sleep(10);

		// GC実行後の状態を表示
		System.out.println(map.toString());
	}
}