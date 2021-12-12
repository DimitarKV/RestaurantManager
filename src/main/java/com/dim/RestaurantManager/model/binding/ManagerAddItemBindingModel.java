package com.dim.RestaurantManager.model.binding;

import com.dim.RestaurantManager.model.validator.annotations.CategoryExists;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class ManagerAddItemBindingModel {
    @CategoryExists
    private String categoryId;
    @URL
    private String imageUrl;
    @Length(min = 4, max = 20)
    private String itemName;
    @Min(0)
    private Double itemPrice;
    @Length(min = 10)
    private String itemDescription;

    public String getCategoryId() {
        return categoryId;
    }

    public ManagerAddItemBindingModel setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ManagerAddItemBindingModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getItemName() {
        return itemName;
    }

    public ManagerAddItemBindingModel setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public ManagerAddItemBindingModel setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
        return this;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public ManagerAddItemBindingModel setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
        return this;
    }
}
