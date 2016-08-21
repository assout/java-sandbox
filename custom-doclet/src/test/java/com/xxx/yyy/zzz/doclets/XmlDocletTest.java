package com.xxx.yyy.zzz.doclets;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.tools.DocumentationTool;
import javax.tools.DocumentationTool.DocumentationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.xml.bind.JAXB;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.xxx.yyy.zzz.doclets.Javadocs.Javadoc;
import com.xxx.yyy.zzz.doclets.Javadocs.Javadoc.LocationType;
import com.xxx.yyy.zzz.doclets.Javadocs.Javadoc.Scope;

public class XmlDocletTest {

	private Path xml;

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Before
	public void before() {
		xml = Paths.get(temporaryFolder.getRoot().getAbsolutePath(), "/jp.xml");
	}

	@Test
	@Ignore("疎通用")
	public void test() throws IOException {
		/* Setup */
		List<String> options = Arrays.asList("-commentpath", xml.toString());

		/* Exercise */
		boolean callTask = callTask(options, TestingFile.FILE_PATH);

		/* Verify */
		assertThat(callTask, is(true));

		Map<String, Javadoc> javadocsMap = getActual(xml).getJavadocsMap();
		System.out.println(javadocsMap);
	}

	@Test
	public void testPackage() throws IOException {
		/* Setup */
		List<String> options = Arrays.asList("-commentpath", xml.toString());

		/* Exercise */
		boolean callTask = callTask(options, TestingFile.PACKAGE_FILE_PATH);

		/* Verify */
		assertThat(callTask, is(true));

		Javadoc expected = new Javadoc(TestingFile.PACKAGE_FQN, TestingFile.PACKAGE_COMMENT, LocationType.Package, null,
				TestingFile.PACKAGE_FILE_PATH, TestingFile.PACKAGE_LINE);
		Javadoc actual = getActual(xml).getJavadocsMap().get(expected.fullyQualifiedName);
		System.out.println(getActual(xml));

		assertThat(actual, is(expected));
	}

	@Test
	public void testClass() throws IOException {
		/* Setup */
		List<String> options = Arrays.asList("-commentpath", xml.toString());

		/* Exercise */
		boolean callTask = callTask(options, TestingFile.FILE_PATH);

		/* Verify */
		assertThat(callTask, is(true));

		Javadoc expected = new Javadoc(TestingFile.CLASS_FQN, TestingFile.CLASS_COMMENT, LocationType.Class,
				Scope.Public, TestingFile.FILE_PATH, TestingFile.CLASS_LINE);
		Javadoc actual = getActual(xml).getJavadocsMap().get(expected.fullyQualifiedName);

		assertThat(actual, is(expected));
	}

	@Test
	public void testField() throws IOException {
		/* Setup */
		List<String> options = Arrays.asList("-commentpath", xml.toString());

		/* Exercise */
		boolean callTask = callTask(options, TestingFile.FILE_PATH);

		/* Verify */
		assertThat(callTask, is(true));

		Javadoc expected = new Javadoc(TestingFile.FIELD_FQN, TestingFile.FIELD_COMMENT, LocationType.Field,
				Scope.Public, TestingFile.FILE_PATH, TestingFile.FIELD_LINE);
		Javadoc actual = getActual(xml).getJavadocsMap().get(expected.fullyQualifiedName);

		assertThat(actual, is(expected));
	}

	@Test
	public void testEnumConstants() throws IOException {
		/* Setup */
		List<String> options = Arrays.asList("-commentpath", xml.toString());

		/* Exercise */
		boolean callTask = callTask(options, TestingFile.FILE_PATH);

		/* Verify */
		assertThat(callTask, is(true));

		Javadoc expected = new Javadoc(TestingFile.FIELD_FQN, TestingFile.FIELD_COMMENT, LocationType.Field,
				Scope.Public, TestingFile.FILE_PATH, TestingFile.FIELD_LINE);
		Javadoc actual = getActual(xml).getJavadocsMap().get(expected.fullyQualifiedName);

		assertThat(actual, is(expected));
	}

	@Test
	public void testConstructor() throws IOException {
		/* Setup */
		List<String> options = Arrays.asList("-commentpath", xml.toString());

		/* Exercise */
		boolean callTask = callTask(options, TestingFile.FILE_PATH);

		/* Verify */
		assertThat(callTask, is(true));

		Javadoc expected = new Javadoc(TestingFile.CONSTRUCTOR_FQN, TestingFile.CONSTRUCTOR_COMMENT,
				LocationType.Constructor, Scope.Public, TestingFile.FILE_PATH, TestingFile.CONSTRUCTOR_LINE);
		Javadoc actual = getActual(xml).getJavadocsMap().get(expected.fullyQualifiedName);

		assertThat(actual, is(expected));
	}

	@Test
	public void testMethod() throws IOException {
		/* Setup */
		List<String> options = Arrays.asList("-commentpath", xml.toString());

		/* Exercise */
		boolean callTask = callTask(options, TestingFile.FILE_PATH);

		/* Verify */
		assertThat(callTask, is(true));

		Javadoc expected = new Javadoc(TestingFile.METHOD_FQN, TestingFile.METHOD_COMMENT, LocationType.Method,
				Scope.Public, TestingFile.FILE_PATH, TestingFile.METHOD_LINE);
		Javadoc actual = getActual(xml).getJavadocsMap().get(expected.fullyQualifiedName);

		assertThat(actual, is(expected));
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

	private boolean callTask(List<String> options, File... files) {
		DocumentationTool tool = ToolProvider.getSystemDocumentationTool();
		StandardJavaFileManager fm = tool.getStandardFileManager(null, null, null);
		try {
			fm.setLocation(DocumentationTool.Location.DOCUMENTATION_OUTPUT, Arrays.asList(temporaryFolder.getRoot()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Iterable<? extends JavaFileObject> javaFileObjects = fm.getJavaFileObjects(files);

		DocumentationTask task = tool.getTask(null, fm, null, XmlDoclet.class, options, javaFileObjects);
		return task.call();
	}

	private Javadocs getActual(Path target) {
		return JAXB.unmarshal(target.toUri(), Javadocs.class);
	}

}
