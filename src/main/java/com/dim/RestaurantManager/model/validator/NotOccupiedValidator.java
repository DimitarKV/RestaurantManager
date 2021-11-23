package com.dim.RestaurantManager.model.validator;

import com.dim.RestaurantManager.service.TableService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Collectors;


public class NotOccupiedValidator implements ConstraintValidator<NotOccupied, Integer> {
    private final TableService tableService;

    public NotOccupiedValidator(TableService tableService) {
        this.tableService = tableService;
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if(value == null)
            return false;
        return tableService.getFreeTable().stream().anyMatch(t -> t.getNumber().equals(value));
    }
}
