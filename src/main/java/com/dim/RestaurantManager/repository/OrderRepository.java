package com.dim.RestaurantManager.repository;

import com.dim.RestaurantManager.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.status.name = com.dim.RestaurantManager.model.entity.enums.OrderStatusEnum.PENDING")
    List<Order> getPendingOrders();

    @Query("SELECT o FROM Order o " +
            "WHERE o.status.name = com.dim.RestaurantManager.model.entity.enums.OrderStatusEnum.COOKING AND o.executor.id = :id")
    List<Order> findCurrentCookOrders(Long id);
}
