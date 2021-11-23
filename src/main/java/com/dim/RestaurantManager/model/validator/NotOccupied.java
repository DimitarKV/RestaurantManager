package com.dim.RestaurantManager.model.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotOccupiedValidator.class)
public @interface NotOccupied {
    String message() default "Table is already occupied!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
