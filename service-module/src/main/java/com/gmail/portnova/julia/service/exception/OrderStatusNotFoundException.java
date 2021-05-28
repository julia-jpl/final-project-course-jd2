package com.gmail.portnova.julia.service.exception;

public class OrderStatusNotFoundException extends ServiceEntityException {
    public OrderStatusNotFoundException(String message) {
        super(message);
    }
}
