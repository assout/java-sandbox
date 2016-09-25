package com.example;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.util.WeldJUnit4Runner;

@RunWith(WeldJUnit4Runner.class)
public class MyApplicationTest {

	@Inject
	Speaker speaker;

	@Test
	public void test() {
		assertThat(speaker, notNullValue());
		speaker.speak();
	}

}
