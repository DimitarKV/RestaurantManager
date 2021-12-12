package com.dim.RestaurantManager.model.validator.annotations;

import com.dim.RestaurantManager.model.validator.BlankOrLengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BlankOrLengthValidator.class)
public @interface BlankOrLength {
    String message() default "Field neither blank nor with the right length!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int min() default 0;

    int max() default Integer.MAX_VALUE;
}
