package com.gmail.portnova.julia.service.exception;

public class ArticleNotFoundException extends ServiceEntityException {
    public ArticleNotFoundException(String message) {
        super(message);
    }
}
