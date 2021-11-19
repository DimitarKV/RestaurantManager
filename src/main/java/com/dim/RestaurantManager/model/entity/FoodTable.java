package com.dim.RestaurantManager.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tables")
public class FoodTable extends BaseEntity {
    private Integer number;
}
