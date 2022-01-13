package com.cashew.payments.exception.handler;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHelper {
	
	@ExceptionHandler(value = {InvalidInputException.class} )
	public ResponseEntity<Object> handleInvalidInputException(InvalidInputException ex) {
		return new ResponseEntity<Object>(new ExceptionDetail(HttpStatus.BAD_REQUEST, ex), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {InSufficientBalanceException.class} )
	public ResponseEntity<Object> handleInSufficientBalanceException(InSufficientBalanceException ex) {
		return new ResponseEntity<Object>(new ExceptionDetail(HttpStatus.UNPROCESSABLE_ENTITY, ex), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(value = {EntityNotFoundException.class} )
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
		return new ResponseEntity<Object>(new ExceptionDetail(HttpStatus.NOT_FOUND, ex), HttpStatus.NOT_FOUND);
	}
}
