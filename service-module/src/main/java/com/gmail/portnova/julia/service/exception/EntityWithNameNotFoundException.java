package com.gmail.portnova.julia.service.exception;

public class EntityWithNameNotFoundException extends RuntimeException{
    public EntityWithNameNotFoundException(String message) {
        super(message);
    }
}
