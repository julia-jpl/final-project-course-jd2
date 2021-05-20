package com.gmail.portnova.julia.service.exception;

public class ImpossibleToDeleteItemException extends RuntimeException {
    public ImpossibleToDeleteItemException(String message) {
        super(message);
    }
}
