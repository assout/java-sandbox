package com.xxx.yyy.zzz.doclets;

import java.nio.file.Path;
import java.util.Map;

import javax.xml.bind.JAXB;

import com.sun.javadoc.AnnotationTypeDoc;
import com.sun.javadoc.AnnotationTypeElementDoc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.MemberDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.RootDoc;
import com.sun.tools.doclets.formats.html.HtmlDoclet;
import com.xxx.yyy.zzz.doclets.Javadocs.Javadoc;

public class LocalizeDoclet extends Doclet {

	private static final Options docletOptions = new Options(); // TODO static?
	private final Map<String, Javadoc> javadocsMap;

	public LocalizeDoclet(Path commentPath) {
		System.out.println("path:" + commentPath);
		Javadocs javadocs = JAXB.unmarshal(commentPath.toUri(), Javadocs.class);
		System.out.println(javadocs);
		javadocsMap = javadocs.getJavadocsMap();
		System.out.println(javadocsMap);
	}

	/**
	 * @see Doclet
	 */
	public static LanguageVersion languageVersion() {
		return docletOptions.languageVersion();
	}

	/**
	 * @see Doclet
	 */
	public static int optionLength(String option) {
		return docletOptions.optionLength(option);
	}

	/**
	 * 副作用があることに注意
	 * 
	 * @see Doclet
	 */
	public static boolean validOptions(String[][] options, DocErrorReporter reporter) {
		boolean validOptions = docletOptions.validOptions(options, reporter);
		if (!validOptions) {
			return false;
		}
		if (docletOptions.getCommentPath().toFile().canRead()) {
			return true;
		} else {
			reporter.printError("Can not read. " + docletOptions.getCommentPath());
			return false;
		}
	}

	/**
	 * @see Doclet
	 */
	public static boolean start(RootDoc root) {
		LocalizeDoclet doclet = new LocalizeDoclet(docletOptions.getCommentPath());
		root.printNotice("Overwriting javadoc informaiton with translation...");
		doclet.processPackages(root.specifiedPackages());
		doclet.processClasses(root.classes());
		root.printNotice("Calling standard doclet...");
		HtmlDoclet.start(root);
		return true;
	}

	private void processPackages(PackageDoc[] docs) {
		for (PackageDoc doc : docs) {
			String key = DocUtil.getKey(doc);
			Javadoc data = javadocsMap.get(key);
			if (data != null) {
				doc.setRawCommentText(data.comment);
			} else {
				System.out.println("nooooooo" + data);
			}

		}
	}

	private void processClasses(ClassDoc[] docs) {
		for (ClassDoc doc : docs) {
			String key = DocUtil.getKey(doc);
			Javadoc data = javadocsMap.get(key);
			if (data != null) {
				doc.setRawCommentText(data.comment);
			}

			processMembers(doc.fields());
			processMembers(doc.enumConstants());
			processMembers(doc.constructors());
			processMembers(doc.methods());

			if (doc.isAnnotationType()) {
				AnnotationTypeElementDoc[] elements = ((AnnotationTypeDoc) doc).elements();
				processMembers(elements);
			}
		}
	}

	private <T extends MemberDoc> void processMembers(T[] members) {
		for (T member : members) {
			String key = DocUtil.getKey(member);
			Javadoc data = javadocsMap.get(key);
			if (data != null) {
				member.setRawCommentText(data.comment);
			}
		}
	}

}