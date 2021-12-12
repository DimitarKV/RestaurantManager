package com.dim.RestaurantManager.model.entity;

import com.dim.RestaurantManager.model.entity.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Item extends BaseEntity {
    @Column(unique = true)
    private String name;
    private Double price;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String imageUrl;
    @OneToOne(mappedBy = "item")
    private MenuItem menuItem;

    public String getName() {
        return name;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Item setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Item setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Item setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public Item setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
        return this;
    }
}
