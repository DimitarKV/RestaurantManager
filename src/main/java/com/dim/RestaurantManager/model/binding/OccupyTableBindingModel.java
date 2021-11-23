package com.dim.RestaurantManager.model.binding;

import com.dim.RestaurantManager.model.validator.NotOccupied;

public class OccupyTableBindingModel {
    @NotOccupied
    private Integer number;

    public Integer getNumber() {
        return number;
    }

    public OccupyTableBindingModel setNumber(Integer number) {
        this.number = number;
        return this;
    }
}
