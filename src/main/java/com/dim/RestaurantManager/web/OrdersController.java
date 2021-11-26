package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.model.view.OrderView;
import com.dim.RestaurantManager.service.UserService;
import com.dim.RestaurantManager.service.impl.RestaurantUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrdersController {
    private final UserService userService;

    public OrdersController(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    @ModelAttribute("orders")
    public List<OrderView> ordersList(@AuthenticationPrincipal RestaurantUser restaurantUser) {
        return userService.getOrders(restaurantUser);
    }

    @GetMapping("/orders")
    public String getOrders() {
        return "orders";
    }


}
