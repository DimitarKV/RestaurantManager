package com.dim.RestaurantManager.model.entity;

import com.dim.RestaurantManager.model.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@javax.persistence.Table(name = "bills")
public class Bill extends BaseEntity {
    @OneToMany(mappedBy = "bill")
    private List<User> users;
    @OneToMany(mappedBy = "bill")
    private List<Order> orders;
    private Double totalPrice;
    @OneToOne
    private FoodTable table;

    public Bill() {
        users = new ArrayList<>();
        orders = new ArrayList<>();
        totalPrice = 0.0;
    }

    public List<User> getUsers() {
        return users;
    }

    public Bill setUsers(List<User> users) {
        this.users = users;
        return this;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Bill setOrders(List<Order> orders) {
        this.orders = orders;
        return this;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public Bill setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public FoodTable getTable() {
        return table;
    }

    public Bill setTable(FoodTable table) {
        this.table = table;
        return this;
    }
}
