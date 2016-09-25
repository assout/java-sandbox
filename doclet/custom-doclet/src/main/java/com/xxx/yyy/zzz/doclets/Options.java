package com.xxx.yyy.zzz.doclets;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.LanguageVersion;
import com.sun.tools.doclets.formats.html.HtmlDoclet;

public class Options {

	private Path commentPath;
	static final String OPTION_COMMENT_PATH = "-commentpath";

	/**
	 * 副作用があることに注意
	 * 
	 * @param options
	 * @param reporter
	 * @return
	 */
	public boolean validOptions(String[][] options, DocErrorReporter reporter) {
		for (String[] option : options) {
			if (option[0].toLowerCase().equals(OPTION_COMMENT_PATH)) {
				commentPath = Paths.get(option[1]);
			}
		}
		if (commentPath == null) {
			commentPath = Paths.get("doc-files/javadoc.xml"); // FIXME
		}

		return HtmlDoclet.validOptions(options, reporter);
	}

	public int optionLength(String option) {
		if (option.equals(OPTION_COMMENT_PATH)) {
			return 2;
		}
		return HtmlDoclet.optionLength(option);
	}

	public LanguageVersion languageVersion() {
		return LanguageVersion.JAVA_1_5;
	}

	public Path getCommentPath() {
		return this.commentPath;
	}
}