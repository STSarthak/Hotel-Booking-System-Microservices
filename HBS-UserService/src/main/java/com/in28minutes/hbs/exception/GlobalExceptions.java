package com.in28minutes.hbs.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptions extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(LocationNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleLocationNotFoundException(Exception ex,WebRequest request) throws Exception{
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(details, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex,WebRequest request) throws Exception{
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(details,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	// Soreve error
	
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		
		List<String> list = ex.getFieldErrors()
								.stream()
								.map(user -> user.getDefaultMessage())
								.collect(Collectors.toList());
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), list.toString(), request.getDescription(false));
		
		return new ResponseEntity<Object>(details,HttpStatus.BAD_REQUEST);
	}
}
