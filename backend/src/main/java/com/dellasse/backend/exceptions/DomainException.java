package com.dellasse.backend.exceptions;

public class DomainException extends RuntimeException {

    private final DomainError error;

    public DomainException(DomainError error) {
        super(error.getMessage());
        this.error = error;
    }

    public int getStatus() {
        return error.getStatus();
    }
}
