package com.dim.RestaurantManager.model.validator;

import com.dim.RestaurantManager.model.validator.annotations.BlankOrLength;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BlankOrLengthValidator implements ConstraintValidator<BlankOrLength, String> {
    private Integer min, max;

    @Override
    public void initialize(BlankOrLength constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null)
            return true;
        if(value.equals(""))
            return true;
        if(value.length() >= min && value.length() <= max)
            return true;
        return false;
    }
}
