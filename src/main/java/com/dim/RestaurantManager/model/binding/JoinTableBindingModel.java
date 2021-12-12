package com.dim.RestaurantManager.model.binding;

import com.dim.RestaurantManager.model.validator.annotations.Occupied;

public class JoinTableBindingModel {
    @Occupied
    private Integer number;

    public Integer getNumber() {
        return number;
    }

    public JoinTableBindingModel setNumber(Integer number) {
        this.number = number;
        return this;
    }
}
