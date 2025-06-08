package com.schoolerp.academic.exception;

public class CustomException extends RuntimeException {

    private int statusCode;
    private String errorMessage;

    public CustomException(int statusCode, String errorMessage) {
        super(errorMessage);
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

