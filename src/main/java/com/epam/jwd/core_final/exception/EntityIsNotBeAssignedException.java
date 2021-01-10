package com.epam.jwd.core_final.exception;

public class EntityIsNotBeAssignedException extends Exception {
    private String name;

    public EntityIsNotBeAssignedException(String message, String nameEntity) {
        super(message);
        this.name = nameEntity;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + ". Name entity is [" + name + "]";
    }
}
