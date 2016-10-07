package sandbox.java.nio;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FilesTest {
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void testGlob() throws Exception {
		Path write = Files.write(folder.getRoot().toPath().resolve("test.txt"), "test".getBytes());

		PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");
		assertThat(pathMatcher.matches(write.getFileName()), is(true));
	}

	@Test
	public void testWalktree() throws Exception {
		Files.write(folder.getRoot().toPath().resolve("test.txt"), "test".getBytes());
		Files.walkFileTree(folder.getRoot().toPath(), new DeleteVisitor(folder.getRoot().toPath()));
	}

	private static class DeleteVisitor extends SimpleFileVisitor<Path> {
		private final Path start;

		public DeleteVisitor(Path start) {
			super();
			this.start = start;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes atts) throws IOException {
			Files.delete(file);
			System.out.println("[DELETE FILE] " + file);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
			if (e != null) {
				throw e;
			}
			if (dir.equals(start)) {
				return FileVisitResult.CONTINUE;
			}

			Files.delete(dir);
			System.out.println("[DELETE DIR] " + dir);
			return FileVisitResult.CONTINUE;
		}
	}
}
