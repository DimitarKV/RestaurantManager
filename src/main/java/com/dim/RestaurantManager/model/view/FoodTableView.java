package com.dim.RestaurantManager.model.view;

public class FoodTableView {
    private Integer number;
    private String description;

    public Integer getNumber() {
        return number;
    }

    public FoodTableView setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FoodTableView setDescription(String description) {
        this.description = description;
        return this;
    }
}
