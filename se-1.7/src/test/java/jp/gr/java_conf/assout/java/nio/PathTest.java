package jp.gr.java_conf.assout.java.nio;

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

	@Test
	public void testGetFileName() throws Exception {
		System.out.println(Paths.get("C:\\test").getFileName());
		System.out.println(Paths.get("D:\\test").getFileName());
		System.out.println(Paths.get("test").getFileName());
		System.out.println(Paths.get("D:").getFileName());
		System.out.println(Paths.get("D:\\admin").getFileName());
		System.out.println(Paths.get("/").getFileName());
		System.out.println(Paths.get("/admin").getFileName());
		System.out.println(Paths.get("/hoge.xml").getFileName());
		System.out.println("absolute");
		System.out.println(Paths.get("C:\\test").toAbsolutePath().getFileName());
		System.out.println(Paths.get("D:\\test").toAbsolutePath().getFileName());
		System.out.println(Paths.get("test").toAbsolutePath().getFileName());
		System.out.println(Paths.get("D:").toAbsolutePath().getFileName());
		System.out.println(Paths.get("D:\\admin").toAbsolutePath().getFileName());
		System.out.println(Paths.get("/").toAbsolutePath().getFileName());
		System.out.println(Paths.get("/admin").toAbsolutePath().getFileName());
		System.out.println(Paths.get("/hoge.xml").toAbsolutePath().getFileName());
	}

}

