package com.dim.RestaurantManager.web.errors;

import com.dim.RestaurantManager.service.exceptions.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({EntityNotFoundException.class})
    public ModelAndView handleEntityNotFoundException(RuntimeException e){
        ModelAndView modelAndView = new ModelAndView("errors/entity-not-persistent");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ModelAndView handleAccessDenied() {
        return new ModelAndView("redirect:/");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ModelAndView handleMethodNotAllowedEx(){
        return new ModelAndView("errors/405");
    }



}
