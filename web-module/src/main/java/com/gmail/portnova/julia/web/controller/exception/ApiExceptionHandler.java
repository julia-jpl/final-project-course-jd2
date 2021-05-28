package com.gmail.portnova.julia.web.controller.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.portnova.julia.service.exception.ServiceEntityException;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = {"com.gmail.portnova.julia.web.controller.api"})
@RequiredArgsConstructor
@Log4j2
public class ApiExceptionHandler {
    private final ObjectMapper objectMapper;

    @ExceptionHandler(ServiceEntityException.class)
    public ResponseEntity<String> handleEntityNotFoundException(ServiceEntityException e) throws JsonProcessingException {
        String exceptionValue = objectMapper.writeValueAsString(e.getMessage());
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(exceptionValue, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle500Exception(Exception exception) throws JsonProcessingException {
        String exceptionValue = objectMapper.writeValueAsString(exception.getMessage());
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(exceptionValue, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
