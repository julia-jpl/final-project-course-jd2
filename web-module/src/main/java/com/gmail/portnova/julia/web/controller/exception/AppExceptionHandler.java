package com.gmail.portnova.julia.web.controller.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gmail.portnova.julia.service.exception.*;
import com.gmail.portnova.julia.web.model.ErrorDetailsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.Session;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    @ExceptionHandler({FeedbackNotFoundException.class,
            CommentNotFoundException.class,
            CommentPersistException.class,
            ItemNotFoundException.class,
            UserRoleNotFoundException.class,
            ArticleNotFoundException.class,
            ItemGroupNotFoundException.class,
            OrderNotFoundException.class,
            OrderStatusNotFoundException.class,
    })
    public ModelAndView handleEntityNotFoundException(ServiceEntityException e, HttpServletRequest request) throws JsonProcessingException {
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


    @ExceptionHandler(Exception.class)
    public ModelAndView handleDefaultException(Exception e, HttpServletRequest request) throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView();
        String requestURI = request.getRequestURI();
        log.error(e.getMessage(), e);
        modelAndView.addObject("url", requestURI);
        modelAndView.setViewName("500error");
        return modelAndView;
    }
}
