package com.rehman.elearning.exceptions.global;

import java.util.ArrayList;
import java.util.List;

import com.rehman.elearning.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.rehman.elearning.util.ErrorApi;

@RestControllerAdvice
public class ApplicationExceptionGlobalHandler {

	@ExceptionHandler({ ApplicationException.class })
	public ResponseEntity<ErrorApi.Response> handleException(ApplicationException e) {
		ErrorApi.Response error = new ErrorApi.Response(e.getCode(), e.getDetails(), System.currentTimeMillis(), null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorApi.Response> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<String> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.add(fieldName + ": " + errorMessage);
		});

		ErrorApi.Response apiError = new ErrorApi.Response(String.valueOf(HttpStatus.BAD_REQUEST.value()),
				"Validation error", System.currentTimeMillis(), errors);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorApi.Response> handleNotFound(NoHandlerFoundException ex) {
		List<String> errors = new ArrayList<>();
		errors.add(ex.getMessage());

		ErrorApi.Response apiError = new ErrorApi.Response(String.valueOf(HttpStatus.NOT_FOUND.value()),
				"Resource not found", System.currentTimeMillis(), errors);
		return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IllegalAccessException.class)
	public ResponseEntity<ErrorApi.Response> handleIllegalAccessException(IllegalAccessException ex) {
		List<String> errors = new ArrayList<>();
		errors.add(ex.getMessage());

		ErrorApi.Response apiError = new ErrorApi.Response(String.valueOf(HttpStatus.FORBIDDEN.value()).toString(),
				"Access denied", System.currentTimeMillis(), errors);
		return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorApi.Response> handleAccessDeniedException(AccessDeniedException ex) {
		List<String> errors = new ArrayList<>();
		errors.add(ex.getMessage());

		ErrorApi.Response apiError = new ErrorApi.Response(String.valueOf(HttpStatus.UNAUTHORIZED.value()),
				"Unauthorized access", System.currentTimeMillis(), errors);
		return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorApi.Response> handleAllExceptions(Exception ex) {
		List<String> errors = new ArrayList<>();
		errors.add(ex.getMessage());
		ErrorApi.Response apiError = new ErrorApi.Response(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
				"Internal Server Error", System.currentTimeMillis(), errors);
		return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
