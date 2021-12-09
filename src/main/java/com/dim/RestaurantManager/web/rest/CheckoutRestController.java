package com.dim.RestaurantManager.web.rest;

import com.dim.RestaurantManager.model.view.CheckoutOrderView;
import com.dim.RestaurantManager.service.OrderService;
import com.dim.RestaurantManager.service.impl.RestaurantUser;
import com.dim.RestaurantManager.web.rest.binding.CheckedOrdersBindingModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CheckoutRestController {
    private final OrderService orderService;

    public CheckoutRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/user/checkout/orders-rest")
    public ResponseEntity<List<CheckoutOrderView>> getOrders(@AuthenticationPrincipal RestaurantUser restaurantUser){
        return ResponseEntity.ok(orderService.getFinishedOrders(restaurantUser.getUsername()));
    }

    @PostMapping("/user/checkout/check-rest")
    public ResponseEntity setCheckedOrders(@AuthenticationPrincipal RestaurantUser restaurantUser,
                                           @RequestBody CheckedOrdersBindingModel bindingModel){
        orderService.handleOrdersByUser(restaurantUser, bindingModel);
        return ResponseEntity.ok().build();
    }

}
