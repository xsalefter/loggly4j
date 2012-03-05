package org.xsalefter.loggly4j.logback.test.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FooService {
	
	private static final Logger logger = LoggerFactory.getLogger(FooService.class);

	private final String message;
	
	public FooService(final String message) {
		logger.info("Create an instance of FooService with parameter: {}", message);
		this.message = message;
	}
	
	public final String getMessage() {
		logger.info("Get a message from FooService: {}", this.message);
		return this.message;
	}
}
