package com.ami.api;

/**
 * @author Amit Khandelwal.
 * Date: 3/2/17
 */
public class AppError {

	private int code;
	private String message;

	public AppError() {
	}

	public AppError(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
