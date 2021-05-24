package com.gmail.portnova.julia.service.exception;

public class EntityWithUuidNotFoundException extends RuntimeException{
    public EntityWithUuidNotFoundException(String message) {
        super(message);
    }
}
