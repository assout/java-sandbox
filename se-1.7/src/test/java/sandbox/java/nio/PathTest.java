package sandbox.java.nio;

import static org.junit.Assert.*;

import java.nio.file.Paths;

import org.junit.Test;

public class PathTest {
	@Test
	public void testGetRoot() throws Exception {
		System.out.println(Paths.get("C:\\test").getRoot());
		System.out.println(Paths.get("D:\\test").getRoot());
		System.out.println(Paths.get("test").getRoot());
		System.out.println(Paths.get("D:").getRoot());
		System.out.println(Paths.get("/").getRoot());
		
		System.out.println(Paths.get("C:\\test").toAbsolutePath().getRoot());
		System.out.println(Paths.get("D:\\test").toAbsolutePath().getRoot());
		System.out.println(Paths.get("test").toAbsolutePath().getRoot());
		System.out.println(Paths.get("D:").toAbsolutePath().getRoot());
		System.out.println(Paths.get("/").toAbsolutePath().getRoot());
	}

}
