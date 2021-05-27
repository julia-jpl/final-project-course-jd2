package com.gmail.portnova.julia.service.exception;

public class UserNotFoundException extends ServiceEntityException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
