package com.dim.RestaurantManager.model.binding;

import com.dim.RestaurantManager.model.validator.annotations.CategoryExists;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ManagerEditItemBindingModel {
    private Long id;
    @CategoryExists
    private Long categoryId;
    @Length(min = 4, max = 30)
    @NotBlank
    private String name;
    @Min(0)
    @NotNull
    private Double price;
    @Length(min = 10)
    @NotBlank
    private String description;
    @URL
    @NotBlank
    private String imageUrl;

    public Long getId() {
        return id;
    }

    public ManagerEditItemBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public ManagerEditItemBindingModel setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getName() {
        return name;
    }

    public ManagerEditItemBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public ManagerEditItemBindingModel setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ManagerEditItemBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ManagerEditItemBindingModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
