package com.llibaiv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DateRangeNotValidException extends Exception {

	private static final long serialVersionUID = 1036571225965170749L;

	public DateRangeNotValidException() {
		super("The start date must be before the end date");
	}
}
