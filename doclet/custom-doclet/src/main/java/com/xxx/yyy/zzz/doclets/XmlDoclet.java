package com.xxx.yyy.zzz.doclets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
import com.sun.javadoc.SourcePosition;
import com.xxx.yyy.zzz.doclets.Javadocs.Javadoc;
import com.xxx.yyy.zzz.doclets.Javadocs.Javadoc.LocationType;

public class XmlDoclet extends Doclet {

	private static final Options docletOptions = new Options(); // static?
	private final Javadocs javadocs = new Javadocs();

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
	 * @see Doclet
	 */
	public static boolean validOptions(String[][] options, DocErrorReporter reporter) {
		return docletOptions.validOptions(options, reporter);
	}

	/**
	 * @see Doclet
	 */
	public static boolean start(RootDoc root) {
		XmlDoclet doclet = new XmlDoclet();
		doclet.processPackages(root.specifiedPackages());
		doclet.processClasses(root.classes());

		try {
			doclet.marshal();
		} catch (IOException e) {
			e.printStackTrace();
			root.printError(e.getMessage());
			return false;
		}

		return true;
	}

	private void marshal() throws IOException {
		Path commentPath = docletOptions.getCommentPath();
		Files.createDirectories(commentPath.getParent());
		JAXB.marshal(javadocs, commentPath.toFile());
	}

	private void processPackages(PackageDoc[] docs) {
		for (PackageDoc doc : docs) {
			String comment = doc.getRawCommentText();
			SourcePosition position = doc.position();
			File file = position == null ? null : position.file();
			Integer line = position == null ? null : position.line();
			Javadoc javadoc = new Javadoc(DocUtil.getKey(doc), comment, Javadoc.LocationType.Package, null, file, line);
			javadocs.add(javadoc);
		}
	}

	private void processClasses(ClassDoc[] docs) {
		for (ClassDoc doc : docs) {
			String classComment = doc.getRawCommentText();
			SourcePosition position = doc.position();
			Javadoc javadoc = new Javadoc(DocUtil.getKey(doc), classComment, Javadoc.LocationType.Class, DocUtil.getScope(doc), position.file(),
					position.line());
			javadocs.add(javadoc);

			processMembers(doc.fields(), LocationType.Field);
			processMembers(doc.enumConstants(), LocationType.EnumConst);
			processMembers(doc.constructors(), LocationType.Constructor);
			processMembers(doc.methods(), LocationType.Method);

			if (doc.isAnnotationType()) {
				AnnotationTypeElementDoc[] elements = ((AnnotationTypeDoc) doc).elements();
				processMembers(elements, LocationType.AnnotationType);
			}
		}
	}

	private <T extends MemberDoc> void processMembers(T[] members, LocationType location) {
		for (T member : members) {
			String comment = member.getRawCommentText();
			SourcePosition position = member.position();
			Javadoc javadoc = new Javadoc(DocUtil.getKey(member), comment, location, DocUtil.getScope(member), position.file(), position.line());
			javadocs.add(javadoc);
		}
	}
}