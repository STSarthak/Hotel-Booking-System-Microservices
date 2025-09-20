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

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(DetailsNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleDetailsNotFoundException(Exception ex, WebRequest request){
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(details,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorDetails> hangleConstraintViolationException(ConstraintViolationException ex, WebRequest request){
		List<String> errorDetails = ex.getConstraintViolations()
										.stream()
										.map(error -> error.getMessage())
										.collect(Collectors.toList());
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), errorDetails.toString(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(details,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleAllEntityExceptions(Exception ex, WebRequest request){
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(details,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> errorList =  ex.getFieldErrors()
									.stream()
									.map(error -> error.getDefaultMessage())
									.collect(Collectors.toList());
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), errorList.toString(), request.getDescription(false));
		return new ResponseEntity<Object>(details,HttpStatus.BAD_REQUEST);
	}

}
