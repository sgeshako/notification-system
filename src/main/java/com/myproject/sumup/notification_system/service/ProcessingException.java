package com.myproject.sumup.notification_system.service;

/**
 * Indicates an error occurred during processing of Message from queue.
 */
public class ProcessingException extends Exception {

	public ProcessingException(String message) {
		super(message);
	}

	public ProcessingException(String message, Throwable cause) {
		super(message, cause);
	}
}
