package jp.gr.java_conf.assout.rules;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

public class VerifyErrorTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();
	@Rule
	public ErrorCollector collector = new ErrorCollector();

	@Test
	public void testMultiExcepitonWithExpectedException() throws Exception {
		exception.expect(NullPointerException.class);
		if (true) {
			throw new NullPointerException();
		}
	}

	@Test
	public void testMultiExceptionWithErrorCollector() throws Exception {
		try {
			this.throwNFE();
		} catch (NumberFormatException e) {
			collector.addError(e);
		}
		collector.checkThat(2, is(1));
	}

	private void throwNFE() throws NumberFormatException {
		throw new NumberFormatException();
	}
}
