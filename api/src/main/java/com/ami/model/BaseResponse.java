package com.ami.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author: Amit Khandelwal
 * Date: 3/2/17
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse  {
	private Object metadata;
	private Object output;
	private AppError error;

	public BaseResponse() {

	}

	public Object getMetadata() {
		return metadata;
	}

	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}

	public Object getOutput() {
		return output;
	}

	public void setOutput(Object output) {
		this.output = output;
	}

	public AppError getError() {
		return error;
	}

	public void setError(AppError error) {
		this.error = error;
	}
}

