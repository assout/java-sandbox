package com.sandbox;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openjdk.jmh.annotations.GenerateMicroBenchmark;

public class JMHTest {
	/**
	 * http://d.hatena.ne.jp/Kazuhira/20140102/1388662362
	 */
	@GenerateMicroBenchmark
	public void simpleBench() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(500);
	}

}
