package com.company.Incident.exception;

public class BadRequestExceptionHandler extends RuntimeException {
    public BadRequestExceptionHandler(String message) {
        super(message);
    }

    public BadRequestExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }
}
