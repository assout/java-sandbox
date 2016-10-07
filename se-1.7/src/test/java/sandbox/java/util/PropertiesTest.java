package sandbox.java.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class PropertiesTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void test文字化け() throws Exception {
		{
			Properties p = new Properties();
			p.load(this.getClass().getClassLoader().getResourceAsStream("java/util/test.properties"));
			System.out.println(p);
		}
		{
			Properties p = new Properties();
			p.load(this.getClass().getClassLoader().getResourceAsStream("java/util/test2.properties"));
			System.out.println(p);
		}
	}

	@Test
	public void test文字コード指定() throws Exception {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("java/util/test.properties");
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		Properties p = new Properties();
		p.load(isr);
		System.out.println(p);
	}

	@Test
	public void testSimple() throws Exception {
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
