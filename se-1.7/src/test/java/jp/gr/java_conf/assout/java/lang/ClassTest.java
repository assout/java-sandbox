package jp.gr.java_conf.assout.java.lang;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.net.URL;

import org.junit.Test;

public class ClassTest {
	@Test
	public void testGetResource() throws Exception {
		{
			URL resource = ClassTest.class.getResource("test.txt");
			assertThat(resource, notNullValue());
		}
		{
			URL resource = ClassTest.class.getClassLoader().getResource("root.txt");
			assertThat(resource, notNullValue());
		}
	}

}
