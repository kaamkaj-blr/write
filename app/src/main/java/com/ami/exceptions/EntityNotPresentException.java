package com.ami.exceptions;

/**
 * @author: Amit Khandelwal.
 * Date: 3/2/17
 */

public class EntityNotPresentException extends RuntimeException {
	private String message;

	public EntityNotPresentException(String message) {
		super(message);
	}

	public EntityNotPresentException(String message, Throwable cause) {
		super(message,cause);
	}
}
