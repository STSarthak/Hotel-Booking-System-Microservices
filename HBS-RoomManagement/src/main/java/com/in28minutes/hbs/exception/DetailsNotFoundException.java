package com.in28minutes.hbs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DetailsNotFoundException extends RuntimeException{
	
	public DetailsNotFoundException(String msg) {
		super(msg);
	}
}
