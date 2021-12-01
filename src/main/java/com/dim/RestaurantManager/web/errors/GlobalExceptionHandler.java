package com.dim.RestaurantManager.web.errors;

import com.dim.RestaurantManager.service.exceptions.EntityNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({EntityNotFoundException.class})
    public ModelAndView handleEntityNotFoundException(RuntimeException e){
        ModelAndView modelAndView = new ModelAndView("errors/entity-not-persistent");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }
}
