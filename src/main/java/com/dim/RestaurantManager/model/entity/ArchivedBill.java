package com.dim.RestaurantManager.model.entity;

import com.dim.RestaurantManager.model.entity.base.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class ArchivedBill extends BaseEntity {
    @ManyToMany(mappedBy = "archivedBills")
    private List<User> users;
    @OneToMany(mappedBy = "bill")
    private List<Order> orders;
    private Double totalPrice;
    @ManyToOne(optional = false)
    private FoodTable table;
    @Column(nullable = false)
    private LocalDateTime archivedOn;

    public ArchivedBill() {
        users = new ArrayList<>();
        orders = new ArrayList<>();
        totalPrice = 0.0;
    }

    public List<User> getUsers() {
        return users;
    }

    public ArchivedBill setUsers(List<User> users) {
        this.users = users;
        return this;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public ArchivedBill setOrders(List<Order> orders) {
        this.orders = orders;
        return this;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public ArchivedBill setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public FoodTable getTable() {
        return table;
    }

    public ArchivedBill setTable(FoodTable table) {
        this.table = table;
        return this;
    }

    public LocalDateTime getArchivedOn() {
        return archivedOn;
    }

    public ArchivedBill setArchivedOn(LocalDateTime archivedOn) {
        this.archivedOn = archivedOn;
        return this;
    }
}
