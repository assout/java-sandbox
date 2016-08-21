package com.xxx.yyy.zzz.doclets;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.LanguageVersion;
import com.sun.tools.doclets.formats.html.ConfigurationImpl;

public class Configuration2 extends ConfigurationImpl {

//	private Path commentPath;
//	static final String OPTION_COMMENT_PATH = "-commentpath";
//
//	@Override
//	public void setSpecificDocletOptions(String[][] options) {
//		for (String[] option : options) {
//			if (option[0].toLowerCase().equals(OPTION_COMMENT_PATH)) {
//				commentPath = Paths.get(option[1]);
//			}
//		}
//		if (commentPath == null) {
//			commentPath = Paths.get("javadoc.xml");
//		}
//
//		super.setSpecificDocletOptions(options);
//	}
//
//	@Override
//	public boolean validOptions(String[][] options, DocErrorReporter reporter) {
//		return super.validOptions(options, reporter);
//	}
//
//	@Override
//	public int optionLength(String option) {
//		if (option.equals(OPTION_COMMENT_PATH)) {
//			return 2;
//		}
//		return super.optionLength(option);
//	}
//
//	public LanguageVersion languageVersion() {
//		return LanguageVersion.JAVA_1_5;
//	}
//
//	/**
//	 * nullは返さない
//	 */
//	public Path getCommentPath() {
//		return this.commentPath;
//	}
}