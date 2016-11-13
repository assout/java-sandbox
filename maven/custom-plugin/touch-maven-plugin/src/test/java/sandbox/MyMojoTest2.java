package sandbox;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

public class MyMojoTest2 extends AbstractMojoTestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void test() {
		System.out.println("aaa");
		fail("Not yet implemented");
	}

	// @Test
	// public void testMojoHasHelpGoal() throws Exception {
	// // src/test/projects/project/pom.xml に書かれた設定を元にMojoインスタンスを作成
	// File baseDir = resources.getBasedir("project");
	// File pom = new File(baseDir, "pom.xml");
	//
	// // 'help' ゴールを実行
	// Mojo mojo = mojoRule.lookupMojo("help", pom);
	// mojo.execute();
	// }
}
