package com.gmail.portnova.julia.service.exception;

public class OrderNotFoundException extends ServiceEntityException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
