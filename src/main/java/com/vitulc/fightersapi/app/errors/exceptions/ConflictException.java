package com.vitulc.fightersapi.app.errors.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException(String errorMessage) {
        super(errorMessage);
    }
}

