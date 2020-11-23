package com.example.exception;

/**
 * This is a custom exception class for throwing validation exceptions.
 */
public class AppException extends Exception {
    private final String message;

    public AppException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
