package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrdersController {
    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/user/orders")
    public String getOrders() {
        return "orders";
    }
}
