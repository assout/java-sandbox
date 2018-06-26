package jp.gr.java_conf.assout.java;

import java.util.Arrays;

import org.junit.Test;

public class MainTest {
	public static void main(String[] args) {
		System.err.println("start");

		System.err.println("args!");
		Arrays.asList(args).forEach(System.err::println);

		System.err.println("props!");
		System.getProperties().values().forEach(System.err::println);

		System.err.println("end");
	}

	@Test
	public void testName() throws Exception {

		String property = System.getProperty("java.class.path");
		String canonicalName = MainTest.class.getCanonicalName();

		ProcessBuilder pb = new ProcessBuilder("java", "-Dfoo=bar,-Daaa=bbb", "-cp", property, canonicalName, "hoge piyo", "baz");
		pb.inheritIO();
		Process start = pb.start();
		start.waitFor();
	}
}
