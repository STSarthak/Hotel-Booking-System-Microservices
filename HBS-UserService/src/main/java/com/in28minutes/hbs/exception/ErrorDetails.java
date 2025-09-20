package com.in28minutes.hbs.exception;

import java.time.LocalDateTime;

public class ErrorDetails {
	private LocalDateTime errorTime;
	private String error;
	private String description;
	
	public ErrorDetails(LocalDateTime errorTime, String error, String description) {
		super();
		this.errorTime = errorTime;
		this.error = error;
		this.description = description;
	}

	public LocalDateTime getErrorTime() {
		return errorTime;
	}

	public void setErrorTime(LocalDateTime errorTime) {
		this.errorTime = errorTime;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
