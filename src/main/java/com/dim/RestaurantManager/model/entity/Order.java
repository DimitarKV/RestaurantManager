package com.dim.RestaurantManager.model.entity;


import com.dim.RestaurantManager.model.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @ManyToOne(optional = false)
    private Item item;
    @ManyToOne(optional = false)
    private Bill bill;
    @ManyToOne(optional = false)
    private OrderStatus status;
    @Column(nullable = false)
    private LocalDateTime placed;

    public Item getItem() {
        return item;
    }

    public Order setItem(Item item) {
        this.item = item;
        return this;
    }

    public Bill getBill() {
        return bill;
    }

    public Order setBill(Bill bill) {
        this.bill = bill;
        return this;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Order setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getPlaced() {
        return placed;
    }

    public Order setPlaced(LocalDateTime placed) {
        this.placed = placed;
        return this;
    }
}
