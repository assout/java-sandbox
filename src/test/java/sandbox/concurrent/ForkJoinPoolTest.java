package sandbox.concurrent;

import static org.junit.Assert.*;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

// refs: http://d.hatena.ne.jp/seraphy/20140504
public class ForkJoinPoolTest {

	/**
	 * タスク内からタスクを生成する最大深さ
	 */
	private static final int maxDepth = 4;

	/**
	 * 実行中、もしくは待機中のタスクの数.
	 */
	private static AtomicInteger activeTaskCnt = new AtomicInteger(0);

	/**
	 * タスクを生成して返す.
	 * 
	 * @param no
	 *            タスクの識別子、深さも表す
	 * @return タスク
	 */
	private static ForkJoinTask<?> createRecursiveTask(final String no) {
		return new RecursiveAction() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void compute() {
				int numOfEntered = activeTaskCnt.incrementAndGet();
				log("[enter] activeTask=" + numOfEntered);
				try {
					if (no.length() < maxDepth) {
						// 最大深さに達していなければ、さらに10個の子タスクを生成する.
						ForkJoinTask<?>[] tasks = new ForkJoinTask<?>[10];
						for (int idx = 0; idx < tasks.length; idx++) {
							tasks[idx] = createRecursiveTask(no + idx);
						}
						// 子タスクをすべて一括実行し、それを待機する.
						// (内部的にはforkしてjoinしている)
						invokeAll(tasks);
					}
				} finally {
					numOfEntered = activeTaskCnt.decrementAndGet();
					log("[leave] activeTask=" + numOfEntered);
				}
			}

			/**
			 * 診断メッセージ表示用
			 * 
			 * @param prefix
			 */
			private void log(String prefix) {
				// 1つのスレッドの使われ方を見るために、特定のスレッドだけを診断メッセージを表示する。
				if (Thread.currentThread().toString()
						.equals("Thread[ForkJoinPool-1-worker-1,5,main]")) {
					StringBuilder buf = new StringBuilder();
					buf.append(prefix);
					buf.append(" no.").append(no);
					buf.append(" :numOfActive=").append(getPool().getActiveThreadCount());
					buf.append(" :poolSize=").append(getPool().getPoolSize());
					buf.append(" :").append(Thread.currentThread());
					System.out.println(buf.toString());
				}
			}
		};
	}

//	public static void main(String[] args) throws Exception {
	@Test
	public void testMain() throws Exception {
		// ForkJoinスレッドプール作成、デフォルトは論理CPU数
		ForkJoinPool forkJoinPool = new ForkJoinPool();

		// 最初のタスクを生成し、完了を待つ
		forkJoinPool.invoke(createRecursiveTask("0"));

		// スレッドプールの終了
		forkJoinPool.shutdown();
		forkJoinPool.awaitTermination(10, TimeUnit.SECONDS);
		System.out.println("done");
	}
}
