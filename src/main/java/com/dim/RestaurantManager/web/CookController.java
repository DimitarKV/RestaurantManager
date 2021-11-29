package com.dim.RestaurantManager.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CookController {

    @GetMapping("/cooks/orders-page")
    public String getCookPage(){
        return "/cook-orders";
    }
}
