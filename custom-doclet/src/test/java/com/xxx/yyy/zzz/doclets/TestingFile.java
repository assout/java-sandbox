package com.xxx.yyy.zzz.doclets;

import java.io.File;

/** Class comment */
public class TestingFile {

	static final File PACKAGE_FILE_PATH = new File("src/test/java/com/xxx/yyy/zzz/doclets/package-info.java");
	static final String PACKAGE_FQN = TestingFile.class.getPackage().getName();
	static final String PACKAGE_COMMENT = "Package comment ";
	static final int PACKAGE_LINE = 2;

	static final File FILE_PATH = new File("src/test/java/com/xxx/yyy/zzz/doclets/TestingFile.java");
	static final String CLASS_FQN = TestingFile.class.getCanonicalName();
	static final String CLASS_COMMENT = "Class comment ";
	static final int CLASS_LINE = 6;

	/** Field comment */
	public static final String TESTING_FIELD = "";
	static final String FIELD_FQN = CLASS_FQN + ".TESTING_FIELD";
	static final String FIELD_COMMENT = "Field comment ";
	static final int FIELD_LINE = 19;

	/** Enum class comment */
	public enum TestingEnum {
		/** Enum const comment */
		ITEM
	}

	static final String ENUM_CONST_FQN = CLASS_FQN + "TestingEnum.ITEM";
	static final String ENUM_CONST_COMMENT = "Enum const comment ";
	static final int ENUM_CONST_LINE = 27;

	/** Constructor comment */
	public TestingFile() {
	}

	static final String CONSTRUCTOR_FQN = PACKAGE_FQN + ".TestingFile()";
	static final String CONSTRUCTOR_COMMENT = "Constructor comment ";
	static final int CONSTRUCTOR_LINE = 34;

	/** Method comment */
	public void testingMethod() {
	}

	static final String METHOD_FQN = CLASS_FQN + ".testingMethod()";
	static final String METHOD_COMMENT = "Method comment ";
	static final int METHOD_LINE = 43;
}
