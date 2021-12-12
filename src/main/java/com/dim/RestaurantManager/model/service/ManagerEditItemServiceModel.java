package com.dim.RestaurantManager.model.service;

public class ManagerEditItemServiceModel {
    private Long id;
    private Long categoryId;
    private String name;
    private Double price;
    private String description;
    private String imageUrl;

    public Long getId() {
        return id;
    }

    public ManagerEditItemServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public ManagerEditItemServiceModel setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getName() {
        return name;
    }

    public ManagerEditItemServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public ManagerEditItemServiceModel setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ManagerEditItemServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ManagerEditItemServiceModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
