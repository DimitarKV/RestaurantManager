package com.dim.RestaurantManager.web.rest;

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
public class CookOrdersRestController {
    private final OrderService orderService;
    private final UserService userService;

    public CookOrdersRestController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PreAuthorize("isCook()")
    @GetMapping("/personnel/cook/orders")
    public ResponseEntity<List<CookOrderView>> getOrders() {
        return ResponseEntity.ok(orderService.getPendingOrders());
    }

    @PreAuthorize("isCook()")
    @GetMapping("/personnel/cook/current/orders")
    public ResponseEntity<List<CookOrderView>> getCurrentCookOrders(@AuthenticationPrincipal RestaurantUser restaurantUser) {
        return ResponseEntity.ok(orderService.getCurrentCookOrders(restaurantUser));
    }

    @PreAuthorize("isCook()")
    @GetMapping("/personnel/cook/order/{orderId}/accept")
    public ResponseEntity acceptOrder(@PathVariable(name = "orderId") String orderId,
            @AuthenticationPrincipal RestaurantUser user) {
        userService.acceptCookOrder(user, Long.parseLong(orderId));
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isCook()")
    @GetMapping("/personnel/cook/order/{orderId}/ready")
    public ResponseEntity readyOrder(@PathVariable(name = "orderId") String orderId) {
        userService.readyCookOrder(Long.parseLong(orderId));
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isCook()")
    @GetMapping("/personnel/cook/order/{orderId}/cancel")
    public ResponseEntity cancelOrder(@PathVariable(name = "orderId") String orderId) {
        userService.cancelCookOrder(Long.parseLong(orderId));
        return ResponseEntity.ok().build();
    }
}

