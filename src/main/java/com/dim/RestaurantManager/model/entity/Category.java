package com.dim.RestaurantManager.model.entity;

import com.dim.RestaurantManager.model.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }
}
