package com.xxx.yyy.zzz.doclets;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

// TODO dirty
public class Javadocs {

	// TODO 冗長保持しているが統一できないか
	@XmlElement(name = "javadoc")
	private final List<Javadoc> javadocs = new ArrayList<>();
	private final LinkedHashMap<String, Javadoc> javadocsMap = new LinkedHashMap<>();

	// for jaxb
	Javadocs() {
	}

	public Javadocs(Javadoc... javadocs) {
		for (Javadoc javadoc : javadocs) {
			this.javadocs.add(javadoc);
			this.javadocsMap.put(javadoc.fullyQualifiedName, javadoc);
		}
	}

	// TODO 防御的コピーを返さなくてよいか
	Map<String, Javadoc> getJavadocsMap() {
		if (javadocs.size() != javadocsMap.size()) {
			for (Javadoc javadoc : javadocs) {
				javadocsMap.put(javadoc.fullyQualifiedName, javadoc);
			}
		}

		return javadocsMap;
	}

	public void add(Javadoc javadoc) {
		javadocs.add(javadoc);
		javadocsMap.put(javadoc.fullyQualifiedName, javadoc);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((javadocs == null) ? 0 : javadocs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Javadocs other = (Javadocs) obj;
		if (javadocs == null) {
			if (other.javadocs != null)
				return false;
		} else if (!javadocs.equals(other.javadocs))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Javadocs [javadocs=" + javadocs + "]";
	}

	@XmlRootElement(name = "javadoc")
	public static class Javadoc {

		@XmlAttribute
		@NotNull
		public final String fullyQualifiedName;
		@XmlValue
		@NotNull
		public final String comment;
		@XmlAttribute
		public final LocationType locationType;
		@XmlAttribute
		public final Scope scope;
		@XmlAttribute
		public final File sourceFile;
		@XmlAttribute
		public final Integer lineNumber;

		// for jaxb
		Javadoc() {
			fullyQualifiedName = null;
			comment = null;
			locationType = null;
			scope = null;
			sourceFile = null;
			lineNumber = null;
		}

		public Javadoc(String fullyQualifiedName, String comment, LocationType locationType, Scope scope,
				File sourceFile, Integer lineNumber) {
			super();
			Objects.requireNonNull(fullyQualifiedName);
			Objects.requireNonNull(comment);

			this.fullyQualifiedName = fullyQualifiedName;
			this.comment = comment;
			this.locationType = locationType;
			this.scope = scope;
			this.sourceFile = sourceFile;
			this.lineNumber = lineNumber;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((comment == null) ? 0 : comment.hashCode());
			result = prime * result + ((fullyQualifiedName == null) ? 0 : fullyQualifiedName.hashCode());
			result = prime * result + ((lineNumber == null) ? 0 : lineNumber.hashCode());
			result = prime * result + ((locationType == null) ? 0 : locationType.hashCode());
			result = prime * result + ((scope == null) ? 0 : scope.hashCode());
			result = prime * result + ((sourceFile == null) ? 0 : sourceFile.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Javadoc other = (Javadoc) obj;
			if (comment == null) {
				if (other.comment != null)
					return false;
			} else if (!comment.equals(other.comment))
				return false;
			if (fullyQualifiedName == null) {
				if (other.fullyQualifiedName != null)
					return false;
			} else if (!fullyQualifiedName.equals(other.fullyQualifiedName))
				return false;
			if (lineNumber == null) {
				if (other.lineNumber != null)
					return false;
			} else if (!lineNumber.equals(other.lineNumber))
				return false;
			if (locationType != other.locationType)
				return false;
			if (scope != other.scope)
				return false;
			if (sourceFile == null) {
				if (other.sourceFile != null)
					return false;
			} else if (!sourceFile.equals(other.sourceFile))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Javadoc [fullyQualifiedName=" + fullyQualifiedName + ", comment=" + comment + ", locationType="
					+ locationType + ", scope=" + scope + ", sourceFile=" + sourceFile + ", lineNumber=" + lineNumber
					+ "]";
		}

		// TODO Java標準ライブラリに定数ないか
		public enum LocationType {
			Package, Class, Method, Constructor, Field, EnumConst, AnnotationType
		}

		// TODO Java標準ライブラリに定数ないか
		public enum Scope {
			Public, Protected, PackagePrivate, Private,
		}

	}
}
