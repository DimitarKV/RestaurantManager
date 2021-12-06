package com.dim.RestaurantManager.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckoutController {
    @GetMapping("/user/checkout")
    public String paymentPage() {
        return "/checkout";
    }
}
