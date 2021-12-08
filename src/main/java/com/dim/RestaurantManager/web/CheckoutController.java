package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.service.OrderService;
import com.dim.RestaurantManager.service.impl.RestaurantUser;
import com.dim.RestaurantManager.web.rest.binding.CheckedOrdersBindingModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;

@Controller
public class CheckoutController {
    private final OrderService orderService;

    public CheckoutController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/user/checkout")
    public String paymentPage() {
        return "/checkout";
    }

    @Transactional
    @PostMapping("/user/checkout")
    public String revokeBill(@AuthenticationPrincipal RestaurantUser restaurantUser,
                             CheckedOrdersBindingModel bindingModel) {
        orderService.revokeItemsBill(bindingModel, restaurantUser);
        return "redirect:/user/checkout";
    }
}
