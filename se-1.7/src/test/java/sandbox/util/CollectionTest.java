package sandbox.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;

public class CollectionTest {

	private static final Logger LOGGER = Logger.getLogger(CollectionTest.class);

	@Test
	public void testMap() throws Exception {
		MyMap map = new MyMap();
		map.put("1", "foo");
		map.put("2", "bar");

		LOGGER.info(map.toString());
	}
	class MyMap extends HashMap<String, String> {
		@Override
		public Set<java.util.Map.Entry<String, String>> entrySet() {
			LOGGER.info("test");
			return super.entrySet();
		}
	}
}
