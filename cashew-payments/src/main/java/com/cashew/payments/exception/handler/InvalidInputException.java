package com.cashew.payments.exception.handler;

public class InvalidInputException extends RuntimeException {
	
	private static final long serialVersionUID = 465737123706024620L;

	public InvalidInputException() {
		super();
	}

	public InvalidInputException(String message) {
		super(message);
	}
	
	public InvalidInputException(Throwable cause) {
		super(cause);
	}
	
	public InvalidInputException(String message, Throwable cause) {
		super(message, cause);
	}
}
