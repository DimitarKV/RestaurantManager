package com.dim.RestaurantManager.model.view;

public class FoodTableView {
    private String title;
    private Integer number;
    private String imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public FoodTableView setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public FoodTableView setTitle(String title) {
        this.title = title;
        return this;
    }
}
