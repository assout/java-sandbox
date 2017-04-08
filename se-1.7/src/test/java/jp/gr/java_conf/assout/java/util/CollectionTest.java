package jp.gr.java_conf.assout.java.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;

public class CollectionTest {

	private static final Logger LOGGER = Logger.getLogger(CollectionTest.class);

	@Test
	public void testListをSetに変換したとき重複要素はどうなるか() throws Exception {
		ArrayList<String> l = new ArrayList<String>();
		l.add("a");
		l.add("a");
		HashSet<String> s = new HashSet<>(l);

		System.out.println(s);
	}

	@Test
	public void testMap() throws Exception {
		MyMap map = new MyMap();
		map.put("1", "foo");
		map.put("2", "bar");

		LOGGER.info(map.toString());
	}

	class MyMap extends HashMap<String, String> {
		private static final long serialVersionUID = 1L;

		@Override
		public Set<java.util.Map.Entry<String, String>> entrySet() {
			LOGGER.info("test");
			return super.entrySet();
		}
	}
}
