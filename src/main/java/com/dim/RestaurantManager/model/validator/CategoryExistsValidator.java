package com.dim.RestaurantManager.model.validator;

import com.dim.RestaurantManager.model.validator.annotations.CategoryExists;
import com.dim.RestaurantManager.service.CategoryService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CategoryExistsValidator implements ConstraintValidator<CategoryExists, Long> {
    private final CategoryService categoryService;

    public CategoryExistsValidator(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if(value == null)
            return false;
        return categoryService.hasCategory(value);
    }
}
