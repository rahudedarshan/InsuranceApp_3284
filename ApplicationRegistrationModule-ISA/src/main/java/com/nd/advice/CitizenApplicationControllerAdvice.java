package com.nd.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nd.exception.SSNInputException;

@RestControllerAdvice
public class CitizenApplicationControllerAdvice {
	
	@ExceptionHandler(SSNInputException.class)
	public ResponseEntity<String> handleInvalidSSN(SSNInputException exception) {
		
		return new ResponseEntity<String> (exception.getMessage(),HttpStatus.FAILED_DEPENDENCY);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleAllExceptions(Exception exception) {
		
		return new ResponseEntity<String> (exception.getMessage(),HttpStatus.FAILED_DEPENDENCY);
		
	}

}
