package com.dim.RestaurantManager.model.validator.annotations;

import com.dim.RestaurantManager.model.validator.OccupiedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OccupiedValidator.class)
public @interface Occupied {
    String message() default "Table is not occupied!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
