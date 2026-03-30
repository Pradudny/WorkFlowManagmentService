package com.company.Incident.exception;

public class AdminExceptions extends RuntimeException {

    String message;
    String errorCode;

    public AdminExceptions() {

    }

    public AdminExceptions(String message) {
        this.message = message;
    }

    public AdminExceptions(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
