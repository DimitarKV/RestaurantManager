package com.dim.RestaurantManager.model.validator;

import com.dim.RestaurantManager.model.validator.annotations.Occupied;
import com.dim.RestaurantManager.service.TableService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OccupiedValidator implements ConstraintValidator<Occupied, Integer> {
    private final TableService tableService;

    public OccupiedValidator(TableService tableService) {
        this.tableService = tableService;
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if(value == null)
            return false;
        return tableService.getOccupiedTables().stream().anyMatch(t -> t.getNumber().equals(value));
    }
}
