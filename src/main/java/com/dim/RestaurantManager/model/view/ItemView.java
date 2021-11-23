package com.dim.RestaurantManager.model.view;

public class ItemView {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public ItemView setName(String name) {
        this.name = name;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public ItemView setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ItemView setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ItemView setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ItemView setId(Long id) {
        this.id = id;
        return this;
    }
}
