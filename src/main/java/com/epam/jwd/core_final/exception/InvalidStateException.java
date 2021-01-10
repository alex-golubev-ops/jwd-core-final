package com.epam.jwd.core_final.exception;

public class InvalidStateException extends Exception {
    private final String message;

    public InvalidStateException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        String message = "Invalid state exception: " + this.message;
        return message;
    }
}
