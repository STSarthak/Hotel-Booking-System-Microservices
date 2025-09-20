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
public class GlobalExceptions extends ResponseEntityExceptionHandler{

	@ExceptionHandler(LocationNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleLocationNotFoundException(Exception ex,WebRequest request) throws Exception{
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorDetails> toString(ConstraintViolationException constraintViolations,Exception ex, WebRequest request) {

		List<String> list = constraintViolations
									.getConstraintViolations()
									.stream()
									.map(error -> error.getMessageTemplate())
									.collect(Collectors.toList());
		System.out.println(list.toString());
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), list.toString(), request.getDescription(false));
		return new ResponseEntity<>(details,HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex,WebRequest request) throws Exception{
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(details,HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> list = ex.getFieldErrors()
								.stream()
								.map(user -> user.getDefaultMessage())
								.collect(Collectors.toList());
		System.out.println(list.toString());
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), list.toString(), request.getDescription(false));

		return new ResponseEntity<>(details,HttpStatus.BAD_REQUEST);
	}


}
