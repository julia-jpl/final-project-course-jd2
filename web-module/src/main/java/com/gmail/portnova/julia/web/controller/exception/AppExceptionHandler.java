package com.gmail.portnova.julia.web.controller.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gmail.portnova.julia.service.exception.*;
import com.gmail.portnova.julia.web.model.ErrorDetailsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.Session;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice(basePackages = {"com.gmail.portnova.julia.web.controller.web"})
@RequiredArgsConstructor
@Log4j2
public class AppExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(UserNotFoundException e, HttpServletRequest request, Session session) throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView();
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO();
        errorDetails.setLocalDateTime(LocalDateTime.now());
        String name = session.getPrincipal().getName();
        errorDetails.setMessage(String.format("User with username %s was not found", name));
        errorDetails.setDetails(request.getRequestURI());
        log.error(e.getMessage(), e);
        modelAndView.addObject("errorDetails", errorDetails);
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(FeedbackNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(FeedbackNotFoundException e, HttpServletRequest request) throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView();
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO();
        errorDetails.setLocalDateTime(LocalDateTime.now());
        errorDetails.setMessage(e.getMessage());
        errorDetails.setDetails(request.getRequestURI());
        log.error(e.getMessage(), e);
        modelAndView.addObject("errorDetails", errorDetails);
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(CommentNotFoundException e, HttpServletRequest request) throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView();
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO();
        errorDetails.setLocalDateTime(LocalDateTime.now());
        errorDetails.setMessage(e.getMessage());
        errorDetails.setDetails(request.getRequestURI());
        log.error(e.getMessage(), e);
        modelAndView.addObject("errorDetails", errorDetails);
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(CommentPersistException.class)
    public ModelAndView handleEntityNotFoundException(CommentPersistException e, HttpServletRequest request) throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView();
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO();
        errorDetails.setLocalDateTime(LocalDateTime.now());
        errorDetails.setMessage(e.getMessage());
        errorDetails.setDetails(request.getRequestURI());
        log.error(e.getMessage(), e);
        modelAndView.addObject("errorDetails", errorDetails);
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(ItemNotFoundException e, HttpServletRequest request) throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView();
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO();
        errorDetails.setLocalDateTime(LocalDateTime.now());
        errorDetails.setMessage(e.getMessage());
        errorDetails.setDetails(request.getRequestURI());
        log.error(e.getMessage(), e);
        modelAndView.addObject("errorDetails", errorDetails);
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(UserRoleNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(UserRoleNotFoundException e, HttpServletRequest request) throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView();
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO();
        errorDetails.setLocalDateTime(LocalDateTime.now());
        errorDetails.setMessage(e.getMessage());
        errorDetails.setDetails(request.getRequestURI());
        log.error(e.getMessage(), e);
        modelAndView.addObject("errorDetails", errorDetails);
        modelAndView.setViewName("error");
        return modelAndView;
    }


    @ExceptionHandler(ArticleNotFoundException.class)
    public ModelAndView handleArticleNotFoundException(ArticleNotFoundException e, HttpServletRequest request) throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView();
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO();
        errorDetails.setLocalDateTime(LocalDateTime.now());
        errorDetails.setMessage(e.getMessage());
        errorDetails.setDetails(request.getRequestURI());
        log.error(e.getMessage(), e);
        modelAndView.addObject("errorDetails", errorDetails);
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ModelAndView handleDefaultException(HttpServerErrorException e, HttpServletRequest request) throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView();
        String requestURI = request.getRequestURI();
        log.error(e.getMessage(), e);
        modelAndView.addObject("url", requestURI);
        modelAndView.setViewName("500error");
        return modelAndView;
    }
}
