package com.dim.RestaurantManager.web.rest;

import com.dim.RestaurantManager.model.view.WaiterOrderView;
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
public class WaiterOrdersRestController {
    private final OrderService orderService;

    public WaiterOrdersRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("isWaiter()")
    @GetMapping("/personnel/waiter/orders-rest")
    public ResponseEntity<List<WaiterOrderView>> getOrders() {
        return ResponseEntity.ok(orderService.getReadyOrders());
    }

    @PreAuthorize("isWaiter()")
    @GetMapping("/personnel/waiter/current/orders-rest")
    public ResponseEntity<List<WaiterOrderView>> getCurrentCookOrders(@AuthenticationPrincipal RestaurantUser restaurantUser) {
        return ResponseEntity.ok(orderService.getCurrentWaiterOrders(restaurantUser));
    }

    @PreAuthorize("isWaiter()")
    @GetMapping("/personnel/waiter/order/{orderId}/accept-rest")
    public ResponseEntity acceptOrder(@PathVariable(name = "orderId") String orderId,
                                      @AuthenticationPrincipal RestaurantUser user) {
        orderService.acceptWaiterOrder(user, Long.parseLong(orderId));
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isWaiter()")
    @GetMapping("/personnel/waiter/order/{orderId}/ready-rest")
    public ResponseEntity readyOrder(@PathVariable(name = "orderId") String orderId) {
        orderService.finishWaiterOrder(Long.parseLong(orderId));
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isWaiter()")
    @GetMapping("/personnel/waiter/order/{orderId}/cancel-rest")
    public ResponseEntity cancelOrder(@PathVariable(name = "orderId") String orderId) {
        orderService.cancelWaiterOrder(Long.parseLong(orderId));
        return ResponseEntity.ok().build();
    }
}
