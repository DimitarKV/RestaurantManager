package com.dim.RestaurantManager.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "meals")
public class Meal extends BaseEntity {
    private String name;
    private Double price;
    private String imageUrl;
}
