package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.model.view.CookOrderView;
import com.dim.RestaurantManager.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrdersRestController {
    private final OrderService orderService;

    public OrdersRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("isPersonnel()")
    @GetMapping("/cooks/orders")
    public ResponseEntity<List<CookOrderView>> getOrders() {
        return ResponseEntity.ok(orderService.getPendingOrders());
    }
}

