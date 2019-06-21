package com.cashier.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class ContentNotAcceptableException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ContentNotAcceptableException(String message, Throwable cause) {
		super(message, cause);
	}

	public ContentNotAcceptableException(String message) {
		super(message);
	}

}
