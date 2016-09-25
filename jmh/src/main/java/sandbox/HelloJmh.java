package sandbox;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.GenerateMicroBenchmark;

public class HelloJmh {
	public static void main(String[] args) {
		org.openjdk.jmh.Main.main("-i 3 -wi 3 -f 1".split(" "));
	}

	@GenerateMicroBenchmark
	public void simpleBench() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(500);
	}
}
