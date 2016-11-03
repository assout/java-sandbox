package sandbox;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;
import org.junit.Test;

public class MyMojoIT {
	@Test
	public void test() throws Exception {
		File testDir = ResourceExtractor.simpleExtractResources(getClass(), "/basic-it");
		Verifier verifier = new Verifier(testDir.getAbsolutePath());
		List cliOptions = new ArrayList();
		cliOptions.add("-X");
		verifier.executeGoal("package");
		verifier.verifyErrorFreeLog();
		verifier.resetStreams();
	}
}
