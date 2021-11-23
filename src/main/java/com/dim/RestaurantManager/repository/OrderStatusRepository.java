package com.dim.RestaurantManager.repository;

import com.dim.RestaurantManager.model.entity.OrderStatus;
import com.dim.RestaurantManager.model.entity.enums.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    Optional<OrderStatus> findByName(OrderStatusEnum name);
}
