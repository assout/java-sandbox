package sandbox;

import static org.junit.Assert.fail;

import java.io.File;

import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.junit.Rule;
import org.junit.Test;

public class MyMojoTest {
	@Rule
	public MojoRule mojoRule = new MojoRule();

	@Rule
	public TestResources resources = new TestResources();

	@Test
	public void test() {
		fail("Not yet implemented");
	}

//	@Test
//	public void testMojoHasHelpGoal() throws Exception {
//		// src/test/projects/project/pom.xml に書かれた設定を元にMojoインスタンスを作成
//		File baseDir = resources.getBasedir("project");
//		File pom = new File(baseDir, "pom.xml");
//
//		// 'help' ゴールを実行
//		Mojo mojo = mojoRule.lookupMojo("help", pom);
//		mojo.execute();
//	}
}
