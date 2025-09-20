package com.in28minutes.hbs.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.catalina.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.UnexpectedTypeException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(DetailsNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleDetailsNotFoundException(Exception ex, WebRequest request) {
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(details,HttpStatus.NOT_FOUND);
	}
	

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request) {
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(details,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> error = ex.getFieldErrors()
								.stream()
								.map(User -> User.getDefaultMessage())
								.collect(Collectors.toList());
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), error.toString(), request.getDescription(false));
		return new ResponseEntity<>(details,HttpStatus.NOT_ACCEPTABLE);
	}
}
