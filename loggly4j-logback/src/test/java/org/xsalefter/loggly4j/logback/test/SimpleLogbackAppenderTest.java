package org.xsalefter.loggly4j.logback.test;

import org.junit.Assert;
import org.junit.Test;
import org.xsalefter.loggly4j.logback.test.sample.FooService;

public class SimpleLogbackAppenderTest {

	@Test
	public void testFooService() {
		FooService fooService = new FooService("Hello Loggly..!");
		Assert.assertNotNull(fooService);
		
		final String message = fooService.getMessage();
		Assert.assertEquals("Hello Loggly..!", message);
	}
}
