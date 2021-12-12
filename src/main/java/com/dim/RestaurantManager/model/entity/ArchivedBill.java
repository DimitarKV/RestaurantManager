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
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "archivedBill")
    private List<Order> orders;
    private Double totalPrice;
    @ManyToOne(optional = false)
    private FoodTable table;
    @Column(nullable = false)
    private LocalDateTime archivedOn;
    private Boolean printed;

    public ArchivedBill() {
        orders = new ArrayList<>();
        totalPrice = 0.0;
    }

    public User getUser() {
        return user;
    }

    public ArchivedBill setUser(User user) {
        this.user = user;
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

    public Boolean getPrinted() {
        return printed;
    }

    public ArchivedBill setPrinted(Boolean printed) {
        this.printed = printed;
        return this;
    }
}
