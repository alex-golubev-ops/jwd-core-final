package com.epam.jwd.core_final.exception;

public class InputException extends Exception {
    private String fieldName;

    public InputException(String fieldName) {
        super();
        this.fieldName = fieldName;
    }

    @Override
    public String getMessage() {
        return fieldName;
    }
}
