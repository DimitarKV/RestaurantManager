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
            "WHERE o.status.name = com.dim.RestaurantManager.model.entity.enums.OrderStatusEnum.COOKING AND o.cook.id = :id")
    List<Order> findCurrentCookOrders(Long id);

    @Query("SELECT o FROM Order o WHERE o.status.name = com.dim.RestaurantManager.model.entity.enums.OrderStatusEnum.READY")
    List<Order> findReadyOrders();

    @Query("SELECT o FROM Order o " +
            "WHERE o.status.name = com.dim.RestaurantManager.model.entity.enums.OrderStatusEnum.TRAVELING AND o.waiter.id = :id")
    List<Order> findCurrentWaiterOrders(Long id);

    @Query("SELECT o FROM Order o " +
            "WHERE o.bill.id = :billId " +
            "ORDER BY o.status.id ASC")
    List<Order> getOrdersByBillId(Long billId);

    @Query("SELECT o FROM Order o " +
            "WHERE o.bill.id = :billId AND " +
            "o.bill.forPrinting = false ")
    List<Order> findOrdersByBillId(Long billId);
}
