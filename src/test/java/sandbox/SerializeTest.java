package sandbox;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Test;

public class SerializeTest {

	private static final Logger logger = Logger.getLogger(SerializeTest.class);

	@Test
	public void testSerialize() throws Exception {
		assertEquals("foo", "bar");
		assertTrue(!true);
		int num = 10000;
		{
			long start = System.nanoTime();
			for (int i = 0; i < num; i++) {
				byte[] serialized = serialize(new MySerializable(8092));
				Object deserialized = deserialize(serialized);
			}
			long end = System.nanoTime();
			long duration = end - start;
			logger.info(String.format("interface:serialize	duration(sec):%d	duration(millis):%d",
					TimeUnit.NANOSECONDS.toSeconds(duration), TimeUnit.NANOSECONDS.toMillis(duration)));
		}

		{
			long start = System.nanoTime();
			for (int i = 0; i < num; i++) {
				byte[] externalized = externalize(new MyExternalizable(8092));
				MyExternalizable deexternalized = new MyExternalizable();
				deexternalize(externalized, deexternalized);
			}
			long end = System.nanoTime();
			long duration = end - start;
			logger.info(String.format("interface:externalize	duration(sec):%d	duration(millis):%d",
					TimeUnit.NANOSECONDS.toSeconds(duration), TimeUnit.NANOSECONDS.toMillis(duration)));
		}
	}

	@Test
	public void testSerializeMap() throws Exception {
		int num = 10000;
		int size = 8092;
		{
			long start = System.nanoTime();
			for (int i = 0; i < num; i++) {
				MyExternalizableMap target = new MyExternalizableMap();
				target.put(String.valueOf(i), new MySerializable(size));

				byte[] externalized = externalize(target);

				MyExternalizableMap deexternalized = new MyExternalizableMap();
				deexternalize(externalized, deexternalized);
			}
			long end = System.nanoTime();
			long duration = end - start;
			logger.info(String.format("serialize method:all externalize	duration(sec):%d	duration(millis):%d",
					TimeUnit.NANOSECONDS.toSeconds(duration), TimeUnit.NANOSECONDS.toMillis(duration)));
		}

		{
			long start = System.nanoTime();
			for (int i = 0; i < num; i++) {
				MyExternalizableMapByteValue target = new MyExternalizableMapByteValue();
				target.put(String.valueOf(i), serialize(new MySerializable(size)));

				byte[] externalized = externalize(target);

				MyExternalizableMapByteValue deexternalized = new MyExternalizableMapByteValue();
				deexternalize(externalized, deexternalized);
			}
			long end = System.nanoTime();
			long duration = end - start;
			logger.info(String.format("serialize method:value pre serialize	duration(sec):%d	duration(millis):%d",
					TimeUnit.NANOSECONDS.toSeconds(duration), TimeUnit.NANOSECONDS.toMillis(duration)));
		}

	}

	private <T> byte[] serialize(T object) throws IOException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream(8192);
		byteOut.reset();
		try (ObjectOutputStream out = new ObjectOutputStream(byteOut)) {
			out.writeObject(object);
		}

		byte[] data = byteOut.toByteArray();
		return data;
	}

	private <T> T deserialize(byte[] data) throws IOException, ClassNotFoundException {
		ByteArrayInputStream byteIn = new ByteArrayInputStream(data);
		try (ObjectInputStream in = new ObjectInputStream(byteIn)) {
			@SuppressWarnings("unchecked")
			T object = (T) in.readObject();
			return object;
		}
	}

	private static byte[] externalize(Externalizable object) throws IOException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream(8192);
		byteOut.reset();
		try (ObjectOutputStream out = new ObjectOutputStream(byteOut)) {
			object.writeExternal(out);
		}
		byte[] data = byteOut.toByteArray();
		return data;
	}

	private void deexternalize(byte[] data, Externalizable externalizable) throws IOException, ClassNotFoundException {
		ByteArrayInputStream byteIn = new ByteArrayInputStream(data);
		try (ObjectInputStream in = new ObjectInputStream(byteIn)) {
			externalizable.readExternal(in);
		}
	}
}

class MySerializable implements Serializable {
	String data;

	public MySerializable() {
	}

	public MySerializable(int size) {
		byte[] b = new byte[size];
		new Random().nextBytes(b);
		data = new String(b);
	}

	@Override
	public String toString() {
		return "MyObject [data=" + data + "]";
	}
}

class MyExternalizable implements Externalizable {
	String data;

	public MyExternalizable() {
	}

	public MyExternalizable(int size) {
		byte[] b = new byte[size];
		new Random().nextBytes(b);
		data = new String(b);
	}

	@Override
	public String toString() {
		return "MyObject [data=" + data + "]";
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeUTF(data);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		data = in.readUTF();
	}
}

class MyExternalizableMap extends HashMap<String, Object> implements Externalizable {
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(this.size());
		for (Map.Entry<String, Object> entry : entrySet()) {
			out.writeUTF(entry.getKey());
			out.writeObject(entry.getValue());
		}
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		int entrySize = in.readInt();
		for (int i = 0; i < entrySize; i++) {
			String key = in.readUTF();
			Object value = in.readObject();
			this.put(key, value);
		}
	}
}

class MyExternalizableMapByteValue extends HashMap<String, byte[]> implements Externalizable {

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(this.size());
		for (Map.Entry<String, byte[]> entry : entrySet()) {
			out.writeUTF(entry.getKey());
			out.writeInt(entry.getValue().length);
			out.write(entry.getValue());
		}
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		int entrySize = in.readInt();
		for (int i = 0; i < entrySize; i++) {
			String key = in.readUTF();
			int valueLength = in.readInt();
			byte[] value = new byte[valueLength];
			in.readFully(value);
			this.put(key, value);
		}
	}
}
