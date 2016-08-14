package sandbox;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import javax.tools.DocumentationTool;
import javax.tools.DocumentationTool.DocumentationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.junit.Test;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.SeeTag;
import com.sun.javadoc.SourcePosition;
import com.sun.javadoc.Tag;

public class ListClassTest {

	@Test
	public void testName3() throws Exception {
		DocumentationTool tool = ToolProvider.getSystemDocumentationTool();
		DocumentationTask task = tool.getTask(null, null, null, ListClass.class, null, null);
		assertThat(task.call(), is(true));
	}

	@Test
	public void testName2() throws Exception {
		DocumentationTool tool = ToolProvider.getSystemDocumentationTool();
		StandardJavaFileManager fm = tool.getStandardFileManager(null, null, null);
		Iterable<? extends JavaFileObject> javaFileObjects = fm.getJavaFileObjects(new File("src/test/java/sandbox/Dummy.java"));

		DocumentationTask task = tool.getTask(null, null, null, ListClass.class, null, javaFileObjects);
		assertThat(task.call(), is(true));
	}
	@Test
	public void testName() throws Exception {
//		DocumentationTool tool = ToolProvider.getSystemDocumentationTool();
//
//		DiagnosticCollector<JavaFileObject> diag = new DiagnosticCollector<>();
//		try (StandardJavaFileManager fileManager = tool.getStandardFileManager(diag, null, null)) {
//			fileManager.setLocation(DocumentationTool.Location.DOCUMENTATION_OUTPUT, Arrays.asList(outputDir));
//			fileManager.setLocation(StandardLocation.SOURCE_PATH, Arrays.asList(srcDir));
//			Writer out = null; // use STDERR(System.err)
//			Class<?> docletClass = null; // use default doclet
//			Iterable<String> options = Arrays.asList("-private");
//			Iterable<? extends JavaFileObject> compilationUnits = fileManager.list( //
//					StandardLocation.SOURCE_PATH, // location
//					rootPackage, // package name
//					EnumSet.of(JavaFileObject.Kind.SOURCE), // kinds
//					true); // recurse
//			DocumentationTool.DocumentationTask task = tool.getTask(out, fileManager, diag, docletClass, options,
//					compilationUnits);
//			if (task.call())
//				System.out.println("OK");
//			else
//				for (Diagnostic<? extends JavaFileObject> o : diag.getDiagnostics())
//					System.out.println(o.getMessage(null));
//		}
	}

	@Test
	public void testStartRootDoc() {
		RootDoc rootDoc = new RootDoc() {

			public void printWarning(SourcePosition arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			public void printWarning(String arg0) {
				// TODO Auto-generated method stub

			}

			public void printNotice(SourcePosition arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			public void printNotice(String arg0) {
				// TODO Auto-generated method stub

			}

			public void printError(SourcePosition arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			public void printError(String arg0) {
				// TODO Auto-generated method stub

			}

			public Tag[] tags(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			public Tag[] tags() {
				// TODO Auto-generated method stub
				return null;
			}

			public void setRawCommentText(String arg0) {
				// TODO Auto-generated method stub

			}

			public SeeTag[] seeTags() {
				// TODO Auto-generated method stub
				return null;
			}

			public SourcePosition position() {
				// TODO Auto-generated method stub
				return null;
			}

			public String name() {
				// TODO Auto-generated method stub
				return null;
			}

			public boolean isOrdinaryClass() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean isMethod() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean isInterface() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean isIncluded() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean isField() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean isException() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean isError() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean isEnumConstant() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean isEnum() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean isConstructor() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean isClass() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean isAnnotationTypeElement() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean isAnnotationType() {
				// TODO Auto-generated method stub
				return false;
			}

			public Tag[] inlineTags() {
				// TODO Auto-generated method stub
				return null;
			}

			public String getRawCommentText() {
				// TODO Auto-generated method stub
				return null;
			}

			public Tag[] firstSentenceTags() {
				// TODO Auto-generated method stub
				return null;
			}

			public int compareTo(Object arg0) {
				// TODO Auto-generated method stub
				return 0;
			}

			public String commentText() {
				// TODO Auto-generated method stub
				return null;
			}

			public PackageDoc[] specifiedPackages() {
				// TODO Auto-generated method stub
				return null;
			}

			public ClassDoc[] specifiedClasses() {
				// TODO Auto-generated method stub
				return null;
			}

			public PackageDoc packageNamed(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			public String[][] options() {
				// TODO Auto-generated method stub
				return null;
			}

			public ClassDoc[] classes() {
				// TODO Auto-generated method stub
				return null;
			}

			public ClassDoc classNamed(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		// ListClass.start();
	}

}
