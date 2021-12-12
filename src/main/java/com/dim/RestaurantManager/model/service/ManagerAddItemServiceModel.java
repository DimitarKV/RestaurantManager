package com.dim.RestaurantManager.model.service;

public class ManagerAddItemServiceModel {
    private Long categoryId;
    private String imageUrl;
    private String itemName;
    private Double itemPrice;
    private String itemDescription;

    public Long getCategoryId() {
        return categoryId;
    }

    public ManagerAddItemServiceModel setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ManagerAddItemServiceModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getItemName() {
        return itemName;
    }

    public ManagerAddItemServiceModel setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public ManagerAddItemServiceModel setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
        return this;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public ManagerAddItemServiceModel setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
        return this;
    }
}
