package jp.gr.java_conf.assout.java.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Test;

public class ResouceBundleTest {

	@Test
	public void test_normal() throws Exception {
		ResourceBundle bundle = ResourceBundle.getBundle("resource");
		String string = bundle.getString("a");
		assertThat(string, is("1"));
	}

	@Test
	public void test_other_path() throws Exception {
		ResourceBundle bundle = ResourceBundle.getBundle("ResourceBundleTest/resource");
		String string = bundle.getString("a");
		assertThat(string, is("2"));
	}

	@Test
	public void testName() throws Exception {
		ClassLoader c = Thread.currentThread().getContextClassLoader();
		URLClassLoader cc = (URLClassLoader) c;
		Enumeration<URL> resources = cc.getResources("resource");
	}

	@Test
	public void test_classpath_modify() throws Exception {
		// ClassPathModifier.addURL(ClassLoader.getSystemResource("ResourceBundleTest"));
		// ClassPathModifier.addURLInFirst(ClassLoader.getSystemResource("ResourceBundleTest"));
		// ClassPathModifier.addURL(ClassLoader.getSystemResource("ResourceBundleTest/resource.properties"));
		// ClassPathModifier.addURLInFirst(ClassLoader.getSystemResource("ResourceBundleTest/resource.properties"));
		//
		// ClassPathModifier.addFile(Paths.get(ClassLoader.getSystemResource("ResourceBundleTest").toURI()).toFile());
		ClassPathModifier
				.addFileInFirst(Paths.get(ClassLoader.getSystemResource("ResourceBundleTest").toURI()).toFile());
		// ClassPathModifier.addFile(
		// Paths.get(ClassLoader.getSystemResource("ResourceBundleTest/resource.properties").toURI()).toFile());
		// ClassPathModifier.addFileInFirst(
		// Paths.get(ClassLoader.getSystemResource("ResourceBundleTest/resource.properties").toURI()).toFile());
		//
		// ClassPathModifier.addFile(
		// Paths.get(ClassLoader.getSystemResource("ResourceBundleTest").toURI()).toAbsolutePath().toFile());
		// ClassPathModifier.addFileInFirst(
		// Paths.get(ClassLoader.getSystemResource("ResourceBundleTest").toURI()).toAbsolutePath().toFile());
		// ClassPathModifier
		// .addFile(Paths.get(ClassLoader.getSystemResource("ResourceBundleTest/resource.properties").toURI())
		// .toAbsolutePath().toFile());
		// ClassPathModifier.addFileInFirst(
		// Paths.get(ClassLoader.getSystemResource("ResourceBundleTest/resource.properties").toURI())
		// .toAbsolutePath().toFile());

		// String property = System.getProperty("java.class.path");
		// System.err.println(property);

		ResourceBundle bundle = ResourceBundle.getBundle("resource");
		String string = bundle.getString("a");
		assertThat(string, is("2"));
	}
}
