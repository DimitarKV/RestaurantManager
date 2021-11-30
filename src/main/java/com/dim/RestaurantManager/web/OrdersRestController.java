package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.model.view.CookOrderView;
import com.dim.RestaurantManager.service.OrderService;
import com.dim.RestaurantManager.service.UserService;
import com.dim.RestaurantManager.service.impl.RestaurantUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrdersRestController {
    private final OrderService orderService;
    private final UserService userService;

    public OrdersRestController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PreAuthorize("isPersonnel()")
    @GetMapping("/cooks/orders")
    public ResponseEntity<List<CookOrderView>> getOrders() {
        return ResponseEntity.ok(orderService.getPendingOrders());
    }

    @PreAuthorize("isPersonnel()")
    @GetMapping("/cooks/order/{orderId}/accept")
    public ResponseEntity acceptOrder(@PathVariable(name = "orderId") String orderId,
            @AuthenticationPrincipal RestaurantUser user) {
        userService.acceptOrder(user, Long.parseLong(orderId));
        return ResponseEntity.ok().build();
    }
}

