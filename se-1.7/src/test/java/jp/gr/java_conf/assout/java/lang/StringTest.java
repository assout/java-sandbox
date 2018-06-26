package jp.gr.java_conf.assout.java.lang;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class StringTest {

	@Test
	public void testReplaceAll() throws Exception {
		assertThat("hoge/".replaceAll("([^/]$)", "$1/"), is("hoge/"));
		assertThat("h/oge/".replaceAll("([^/]$)", "$1/"), is("h/oge/"));
		assertThat("hoge".replaceAll("([^/]$)", "$1/"), is("hoge/"));
		assertThat("hoge//".replaceAll("([^/]$)", "$1/"), is("hoge//"));
	}
}
