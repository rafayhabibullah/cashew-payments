package com.cashew.payments.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ExceptionDetail {
	
	private HttpStatus httpStatus;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String detailedMessage;
	
	public ExceptionDetail() {
		this.timestamp = LocalDateTime.now();
	}
	
	public ExceptionDetail(HttpStatus httpStatus) {
		this();
		this.httpStatus = httpStatus;
	}
	
	public ExceptionDetail(HttpStatus httpStatus, String message, Throwable ex) {
		this();
		this.httpStatus = httpStatus;
		this.message = message;
		this.detailedMessage = ex.getLocalizedMessage();
	}
	
	public ExceptionDetail(HttpStatus httpStatus, Throwable ex) {
		this();
		this.httpStatus = httpStatus;
		this.message = "Un-Expected error";
		this.detailedMessage = ex.getLocalizedMessage();
	}
	
	public ExceptionDetail(Throwable ex) {
		this();
		this.message = "Un-Expected error";
		this.detailedMessage = ex.getLocalizedMessage();
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetailedMessage() {
		return detailedMessage;
	}

	public void setDetailedMessage(String detailedMessage) {
		this.detailedMessage = detailedMessage;
	}
	
}
