package com.xxx.yyy.zzz.doclets;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.tools.DocumentationTool;
import javax.tools.DocumentationTool.DocumentationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.xml.bind.JAXB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class LocalizeDocletTest {

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Test
//	@Ignore("疎通用")
	public void test() {
		URL resource = LocalizeDocletTest.class.getClassLoader().getResource("TestingFile.xml");
		Javadocs unmarshal = JAXB.unmarshal(resource, Javadocs.class);
		System.out.println(unmarshal);
		List<String> options = Arrays.asList("-commentpath", resource.toString());
		callTask(options, TestingFile.FILE_PATH);
		System.out.println();
	}

	@Test
	public void testInvalidOption() throws IOException {
		/* Setup */
		List<String> options = Arrays.asList("-dummy");

		/* Exercise */
		boolean callTask = callTask(options, TestingFile.FILE_PATH);

		/* Verify */
		assertThat(callTask, is(false));
	}

	@Test
	public void testInvalidOptionArgs_noArgs() throws IOException {
		/* Setup */
		List<String> options = Arrays.asList("-commentpath");

		/* Exercise */
		boolean callTask = callTask(options, TestingFile.FILE_PATH);

		/* Verify */
		assertThat(callTask, is(false));
	}

	@Test
	public void testInvalidOptionArgs_canNotRead() throws IOException {
		/* Setup */
		List<String> options = Arrays.asList("-commentpath", "dummy");

		/* Exercise */
		boolean callTask = callTask(options, TestingFile.FILE_PATH);

		/* Verify */
		assertThat(callTask, is(false));
	}

	private boolean callTask(List<String> options, File... files) {
		DocumentationTool tool = ToolProvider.getSystemDocumentationTool();
		StandardJavaFileManager fm = tool.getStandardFileManager(null, null, null);
		try {
			fm.setLocation(DocumentationTool.Location.DOCUMENTATION_OUTPUT, Arrays.asList(temporaryFolder.getRoot()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Iterable<? extends JavaFileObject> javaFileObjects = fm.getJavaFileObjects(files);

		DocumentationTask task = tool.getTask(null, fm, null, LocalizeDoclet.class, options, javaFileObjects);
		return task.call();
	}
}
