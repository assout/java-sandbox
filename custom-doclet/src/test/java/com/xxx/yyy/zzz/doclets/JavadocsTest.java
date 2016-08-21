package com.xxx.yyy.zzz.doclets;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.xml.bind.JAXB;

import org.junit.Ignore;
import org.junit.Test;

import com.xxx.yyy.zzz.doclets.Javadocs.Javadoc;
import com.xxx.yyy.zzz.doclets.Javadocs.Javadoc.LocationType;
import com.xxx.yyy.zzz.doclets.Javadocs.Javadoc.Scope;

// TODO equals, hashCodeの一般契約テスト
public class JavadocsTest {

	private final ByteArrayOutputStream outSpy = new ByteArrayOutputStream();

	@Test
	public void testName() throws Exception {
		System.out.println(TestingFile.FIELD_FQN);
	}
	@Test
	@Ignore("疎通用")
	public void testMarshal_test() {
		/* Setup */
		Javadocs javadocs = new Javadocs();
		{
			Javadoc javadoc = new Javadoc("test1", "comment1", LocationType.Constructor, Scope.Public,
					new File("testFile1"), 1);
			javadocs.add(javadoc);
		}
		{
			Javadoc javadoc = new Javadoc("test2", "comment2", LocationType.Class, Scope.PackagePrivate,
					new File("testFile2"), 2);
			javadocs.add(javadoc);
		}

		/* Exercise */
		/* Verify */
		JAXB.marshal(javadocs, System.out);
	}

	@Test
	public void testMarshal_normal() {
		/* Setup */
		Javadocs javadocs = new Javadocs();
		{
			Javadoc javadoc = new Javadoc("test1", "comment1", LocationType.Constructor, Scope.Public,
					new File("testFile1"), 1);
			javadocs.add(javadoc);
		}
		{
			Javadoc javadoc = new Javadoc("test2", "comment2", LocationType.Class, Scope.PackagePrivate,
					new File("testFile2"), 2);
			javadocs.add(javadoc);
		}

		/* Exercise */
		JAXB.marshal(javadocs, outSpy);

		/* Verify */
		StringBuilder expected = new StringBuilder();
		expected.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
		expected.append("<javadocs>\n");
		expected.append(
				"    <javadoc fullyQualifiedName=\"test1\" locationType=\"Constructor\" scope=\"Public\" sourceFile=\"testFile1\" lineNumber=\"1\">comment1</javadoc>\n");
		expected.append(
				"    <javadoc fullyQualifiedName=\"test2\" locationType=\"Class\" scope=\"PackagePrivate\" sourceFile=\"testFile2\" lineNumber=\"2\">comment2</javadoc>\n");
		expected.append("</javadocs>\n");

		assertThat(outSpy.toString(), is(expected.toString()));
	}

	@Test
	public void testMarshal_zeroItem() {
		/* Setup */
		Javadocs javadocs = new Javadocs();

		/* Exercise */
		JAXB.marshal(javadocs, outSpy);

		/* Verify */
		StringBuilder expected = new StringBuilder();
		expected.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
		expected.append("<javadocs/>\n");

		assertThat(outSpy.toString(), is(expected.toString()));
	}

	@Test
	public void testMarshal_hasNullItem() {
		/* Setup */
		Javadocs javadocs = new Javadocs(new Javadoc());

		/* Exercise */
		JAXB.marshal(javadocs, outSpy);

		/* Verify */
		StringBuilder expected = new StringBuilder();
		expected.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
		expected.append("<javadocs>\n");
		expected.append("    <javadoc/>\n");
		expected.append("</javadocs>\n");

		assertThat(outSpy.toString(), is(expected.toString()));
	}

	@Test
	@Ignore("疎通用")
	public void testUnmarshal_test() {
		/* Setup */
		StringBuilder source = new StringBuilder();
		source.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
		source.append("<javadocs>\n");
		source.append(
				"    <javadoc fullyQualifiedName=\"test1\" locationType=\"Constructor\" scope=\"Public\" sourceFile=\"testFile1\" lineNumber=\"1\">comment1</javadoc>\n");
		source.append(
				"    <javadoc fullyQualifiedName=\"test2\" locationType=\"Class\" scope=\"PackagePrivate\" sourceFile=\"testFile2\" lineNumber=\"2\">comment2</javadoc>\n");
		source.append("</javadocs>\n");

		/* Exercise */
		StringReader reader = new StringReader(source.toString());
		Javadocs actual = JAXB.unmarshal(reader, Javadocs.class);

		/* Verify */
		System.out.println(actual);
	}

	@Test
	public void testUnmarshal_normal() {
		/* Setup */
		StringBuilder source = new StringBuilder();
		source.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
		source.append("<javadocs>\n");
		source.append(
				"    <javadoc fullyQualifiedName=\"test1\" locationType=\"Constructor\" scope=\"Public\" sourceFile=\"testFile1\" lineNumber=\"1\">comment1</javadoc>\n");
		source.append(
				"    <javadoc fullyQualifiedName=\"test2\" locationType=\"Class\" scope=\"PackagePrivate\" sourceFile=\"testFile2\" lineNumber=\"2\">comment2</javadoc>\n");
		source.append("</javadocs>\n");

		/* Exercise */
		Javadocs actual = JAXB.unmarshal(new StringReader(source.toString()), Javadocs.class);

		/* Verify */
		Javadocs expected = new Javadocs();
		{
			Javadoc javadoc = new Javadoc("test1", "comment1", LocationType.Constructor, Scope.Public,
					new File("testFile1"), 1);
			expected.add(javadoc);
		}
		{
			Javadoc javadoc = new Javadoc("test2", "comment2", LocationType.Class, Scope.PackagePrivate,
					new File("testFile2"), 2);
			expected.add(javadoc);
		}
		assertThat(actual, is(expected));
	}

	@Test
	public void testUnmarshal_zeroItem() {
		/* Setup */
		StringBuilder source = new StringBuilder();
		source.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
		source.append("<javadocs/>\n");

		/* Exercise */
		Javadocs actual = JAXB.unmarshal(new StringReader(source.toString()), Javadocs.class);

		/* Verify */
		Javadocs expected = new Javadocs();
		assertThat(actual, is(expected));
	}

	@Test
	public void testUnmarshal_hasNullItem() {
		/* Setup */
		StringBuilder source = new StringBuilder();
		source.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
		source.append("<javadocs>\n");
		source.append("    <javadoc/>\n");
		source.append("</javadocs>\n");

		/* Exercise */
		Javadocs actual = JAXB.unmarshal(new StringReader(source.toString()), Javadocs.class);

		/* Verify */
		Javadocs expected = new Javadocs(new Javadoc());
		assertThat(actual, is(expected));
	}

	@Test
	public void testGetJavadocsMap() {
		/* Setup */
		Javadocs javadocs = new Javadocs();
		Map<String, Javadoc> expected = new HashMap<>();
		{
			Javadoc javadoc = new Javadoc("test1", "comment1", LocationType.Constructor, Scope.Public,
					new File("testFile1"), 1);
			javadocs.add(javadoc);
			expected.put(javadoc.fullyQualifiedName, javadoc);
		}
		{
			Javadoc javadoc = new Javadoc("test2", "comment2", LocationType.Class, Scope.PackagePrivate,
					new File("testFile2"), 2);
			javadocs.add(javadoc);
			expected.put(javadoc.fullyQualifiedName, javadoc);
		}

		/* Exercise */
		Map<String, Javadoc> actual = javadocs.getJavadocsMap();

		/* Verify */
		assertThat(Objects.equals(actual, expected), is(true));
	}

	@Test
	public void testGetJavadocsMap_zero() {
		/* Setup */
		Javadocs javadocs = new Javadocs();

		/* Exercise */
		Map<String, Javadoc> actual = javadocs.getJavadocsMap();

		/* Verify */
		assertThat(actual.isEmpty(), is(true));
	}
}
