package com.cashew.payments.exception.handler;

public class InSufficientBalanceException extends RuntimeException {
	
	private static final long serialVersionUID = 8364963064612410715L;

	public InSufficientBalanceException() {
		super();
	}

	public InSufficientBalanceException(String message) {
		super(message);
	}
	
	public InSufficientBalanceException(Throwable cause) {
		super(cause);
	}
	
	public InSufficientBalanceException(String message, Throwable cause) {
		super(message, cause);
	}
}
