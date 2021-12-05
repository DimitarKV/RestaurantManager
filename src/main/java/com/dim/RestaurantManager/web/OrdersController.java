package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.model.view.OrderView;
import com.dim.RestaurantManager.service.OrderService;
import com.dim.RestaurantManager.service.impl.RestaurantUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.util.List;

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

    @Transactional
    @GetMapping("/user/orders-rest")
    @ResponseBody
    public ResponseEntity<List<OrderView>> getUserOrders(@AuthenticationPrincipal RestaurantUser restaurantUser){
        return ResponseEntity.ok(orderService.getOrders(restaurantUser));
    }

    @PreAuthorize("isOwner(#orderId)")
    @GetMapping("/user/order/{orderId}/cancel")
    public ResponseEntity cancelUserOrder(@PathVariable(name = "orderId") Long orderId){
        orderService.cancelUserOrder(orderId);
        return ResponseEntity.ok().build();
    }

}
