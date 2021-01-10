package com.epam.jwd.core_final.exception;

public class DuplicateEntityException extends Exception {
    private final String name;

    public DuplicateEntityException(String nameEntity) {
        super();
        this.name = nameEntity;
    }

    @Override
    public String getMessage() {
        return "Found in context entity with duplicate name [" + name + "]";
    }

}
