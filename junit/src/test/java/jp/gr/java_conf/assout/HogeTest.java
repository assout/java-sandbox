package jp.gr.java_conf.assout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class HogeTest {

	@Test
	public void testName() throws Exception {
		System.out.println(String.format("Application \"%s\": Can't delete work dir. dir={%s}", "hoge", "fuga"));
	}

	@Test
	public void testName2() throws Exception {
		String reg = "_[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

		System.out.println("hogehoge".matches(reg));
		System.out.println(
				"hogehogesimple-feature-application-3.0.0-SNAPSHOTa15bb25a-a2a8-4ab9-87ac-ee1ab72a1ef0".matches(reg));
		System.out.println("a15bb25a-a2a8-4ab9-87ac-ee1ab72a1ef0".matches(reg));
		System.out.println("a15bb25a-a2a8-4ab9-87ac-ee1ab72a1ef0HOGEHOGE".matches(reg));
		System.out.println("FUGAGUa15bb25a-a2a8-4ab9-87ac-ee1ab72a1ef0HOGEHOGE".matches(reg));
		System.out.println(
				"hogehogesimple-feature-application-3.0.0-SNAPSHOT_a15bb25a-a2a8-4ab9-87ac-ee1ab72a1ef0".matches(reg));
		System.out.println("_a15bb25a-a2a8-4ab9-87ac-ee1ab72a1ef0".matches(reg));

		System.out.println("hogehoge".replaceFirst(reg, ""));
		System.out.println("hogehogesimple-feature-application-3.0.0-SNAPSHOTa15bb25a-a2a8-4ab9-87ac-ee1ab72a1ef0"
				.replaceFirst(reg, ""));
		System.out.println("a15bb25a-a2a8-4ab9-87ac-ee1ab72a1ef0".replaceFirst(reg, ""));
		System.out.println("a15bb25a-a2a8-4ab9-87ac-ee1ab72a1ef0HOGEHOGE".replaceFirst(reg, ""));
		System.out.println("FUGAGUa15bb25a-a2a8-4ab9-87ac-ee1ab72a1ef0HOGEHOGE".replaceFirst(reg, ""));
		System.out.println("hogehogesimple-feature-application-3.0.0-SNAPSHOT_a15bb25a-a2a8-4ab9-87ac-ee1ab72a1ef0"
				.replaceFirst(reg, ""));
		System.out.println("_a15bb25a-a2a8-4ab9-87ac-ee1ab72a1ef0".replaceFirst(reg, ""));
	}

	@Test
	public void testTry() throws Exception {
		try {
			try {
				System.out.println(1);
				throw new Exception("hoge");
			} catch (Exception e) {
				System.out.println(2);
				throw new Exception("fuga");
			} finally {
				System.out.println(3);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDate() throws Exception {
		System.out.println(new Date(0));
		System.out.println(new Date(Long.MAX_VALUE - 1L));
		System.out.println(new Date(Long.MAX_VALUE));
		System.out.println(new Date(Long.MAX_VALUE + 1L));
	}

	@Test
	public void testDate3() throws Exception {
		Date date = new Date(0);
		Date date2 = new Date(Long.MAX_VALUE - 1L);
		Date date3 = new Date(Long.MAX_VALUE + TimeUnit.SECONDS.toMillis(10L));
		Date date4 = new Date(Long.MAX_VALUE + 1L);
		System.out.println(TimeUnit.SECONDS.toMillis(10L));

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd (EE) HH:mm:ss.SSS zz");
		System.out.println(df.format(date));
		System.out.println(df.format(date2));
		System.out.println(df.format(date3));
		System.out.println(df.format(date4));
	}
}
