package com.gmail.portnova.julia.service.exception;

public class FeedbackNotFoundException extends RuntimeException{
    public FeedbackNotFoundException(String message) {
        super(message);
    }
}
