package sandbox;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;
import com.sun.tools.doclets.formats.html.HtmlDoclet;

public class ListClass extends Doclet {
	public static boolean start(RootDoc root) {
		System.out.println("test");
		for (String[] o : root.options()) {
			for (String s : o) {
				System.out.println(s);
			}
		}

		ClassDoc[] classes = root.classes();
		for (int i = 0; i < classes.length; ++i) {
			System.out.println(classes[i]);
		}
		return HtmlDoclet.start(root);
//		return true;
	}

	public static int optionLength(String option) {
		switch (option) {
//		case "-d":
//			// 標準ドックレット固有のオプションだが、 Ant タスクはデフォルトで付加してくるので未知のオプションにはしない
//			return 1;
		default:
			return HtmlDoclet.optionLength(option);
		}
	}

	public static boolean validOptions(String[][] options, DocErrorReporter reporter) {
		return HtmlDoclet.validOptions(options, reporter);
	}

	public static LanguageVersion languageVersion() {
		return LanguageVersion.JAVA_1_5;
	}
}
