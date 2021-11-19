package com.dim.RestaurantManager.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "drinks")
public class Drink extends BaseEntity {
    private String name;
    private Integer quantity;
    private Double price;
    private String imageUrl;
}
