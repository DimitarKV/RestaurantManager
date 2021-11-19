package com.dim.RestaurantManager.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@javax.persistence.Table(name = "bills")
public class Bill extends BaseEntity {
    @ManyToMany
    @JoinTable(name = "bills_users",
    joinColumns = {@JoinColumn(name = "bill_id")},
    inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> users;
    @ManyToMany
    private List<Meal> meals;
    @ManyToMany
    private List<Drink> drinks;
    private Double totalPrice;
    @OneToOne
    private FoodTable table;
}
