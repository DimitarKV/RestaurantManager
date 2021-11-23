package com.dim.RestaurantManager.model.entity;

import com.dim.RestaurantManager.model.entity.base.BaseEntity;
import com.dim.RestaurantManager.model.entity.enums.OrderStatusEnum;

import javax.persistence.*;

@Entity
@Table(name = "order_statuses")
public class OrderStatus extends BaseEntity {
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum name;

    public OrderStatusEnum getName() {
        return name;
    }

    public OrderStatus setName(OrderStatusEnum name) {
        this.name = name;
        return this;
    }
}
