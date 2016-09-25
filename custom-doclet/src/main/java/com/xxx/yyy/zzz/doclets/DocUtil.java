/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.xxx.yyy.zzz.doclets;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ExecutableMemberDoc;
import com.sun.javadoc.MemberDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.ProgramElementDoc;
import com.xxx.yyy.zzz.doclets.Javadocs.Javadoc.Scope;

public class DocUtil {
	public static String getKey(PackageDoc pd) {
		return pd.name();
	}

	public static String getKey(ClassDoc cd) {
		return cd.qualifiedName();
	}

	public static <T extends MemberDoc> String getKey(T md) {
		if ((md.isField()) || (md.isEnumConstant()) || (md.isAnnotationTypeElement()))
			return md.qualifiedName();
		if ((md.isConstructor()) || (md.isMethod())) {
			return md.qualifiedName() + ((ExecutableMemberDoc) md).signature();
		}
		System.err.println("Failed to getKey for " + md.qualifiedName());
		return "";
	}

	public static Scope getScope(ProgramElementDoc doc) {
		if (doc.isPublic()) {
			return Scope.Public;
		} else if (doc.isProtected()) {
			return Scope.Protected;
		} else if (doc.isPackagePrivate()) {
			return Scope.PackagePrivate;
		} else if (doc.isPrivate()) {
			return Scope.Private;
		} else {
			throw new AssertionError("Unexpected scope. " + doc);
		}
	}
}