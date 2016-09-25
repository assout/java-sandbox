package sandbox.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class PropertiesTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void testName() throws Exception {
		// Propertiesオブジェクトを生成
		Properties properties = new Properties();

		try {
			// ファイルを書き込む
			properties.setProperty("name", "Coco");
			properties.setProperty("age", "１００歳だよ");

			File newFile = folder.newFile("test.properties");
			try (FileOutputStream s = new FileOutputStream(newFile)) {
				properties.store(s, "Comments");
			}

			System.out.println(newFile);
			System.out.println();

			// ファイルを読み込む
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	@Test
	public void testNullComment() throws Exception {
		// Propertiesオブジェクトを生成
		Properties properties = new Properties();

		try {
			// ファイルを書き込む
			properties.setProperty("name", "Coco");
			properties.setProperty("age", "１００歳だよ");

			File newFile = folder.newFile("test.properties");
			try (FileOutputStream s = new FileOutputStream(newFile)) {
				properties.store(s, null);
			}

			System.out.println(newFile);
			System.out.println();

			// ファイルを読み込む
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
}
