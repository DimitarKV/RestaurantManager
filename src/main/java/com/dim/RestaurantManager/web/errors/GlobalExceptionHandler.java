package com.dim.RestaurantManager.web.errors;

import com.dim.RestaurantManager.service.exceptions.EntityNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ModelAndView handleMethodNotAllowedEx(){
        return new ModelAndView("errors/405");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView notFound(){
        return new ModelAndView("errors/404");
    }


}
