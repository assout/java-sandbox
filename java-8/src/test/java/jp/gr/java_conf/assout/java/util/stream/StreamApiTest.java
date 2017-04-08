package jp.gr.java_conf.assout.java.util.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamApiTest {
	@Test
	public void testName() {
		List<String> lists = Arrays.asList("tokyo", "ibaraki", "saitama");
		Stream<String> stream = lists.stream(); // 直列ストリーム
		Stream<String> pstream = lists.parallelStream(); // 並列ストリーム
		stream.forEach(System.out::println);
		System.out.println("---");
		pstream.forEach(System.out::println);
	}
}
