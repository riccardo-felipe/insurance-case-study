package com.casestudy.demo.interfaces;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler{
	private final HttpHeaders headers;

	public ControllerExceptionHandler() {
		this.headers = initializeHeaders();
	}

	@ExceptionHandler(value = EntityExistsException.class)
	public ResponseEntity<Object> handleWhenEntityAlreadyExists(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, parseErrorMessage(ex), new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler(value = EntityNotFoundException.class)
	public ResponseEntity<Object> handleWhenEntityWasNotFound(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, parseErrorMessage(ex), headers, HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<Object> handleWhenAnIllegalArgumentArrives(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, parseErrorMessage(ex), headers, HttpStatus.BAD_REQUEST, request);
	}

	private HttpHeaders initializeHeaders() {
		final HttpHeaders headers = new HttpHeaders();
						  headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	private String parseErrorMessage(RuntimeException ex) {
		return String.format("{\n\t\"error\": \"%s\"\n}", ex.getMessage());
	}
}
