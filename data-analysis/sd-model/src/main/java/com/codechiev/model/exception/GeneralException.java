package com.codechiev.model.exception;

public class GeneralException extends Exception {
	private static final long serialVersionUID = 8842056020814582922L;

	private int errorCode;
	private String message;

	public GeneralException(int errorCode, String message) {
		this.message = message;
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public String getMessage() {
		return this.message;
	}
}
