package com.dim.RestaurantManager.model.entity;

import com.dim.RestaurantManager.model.entity.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "tables")
public class FoodTable extends BaseEntity {
    @Column(nullable = false)
    private Integer number;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private String imageUrl;
    @OneToOne(mappedBy = "table")
    private Bill bill;

    public Integer getNumber() {
        return number;
    }

    public FoodTable setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public Bill getBill() {
        return bill;
    }

    public FoodTable setBill(Bill bill) {
        this.bill = bill;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FoodTable setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public FoodTable setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public FoodTable setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
