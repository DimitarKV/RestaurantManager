package com.dim.RestaurantManager.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WaiterController {
    @GetMapping("/personnel/waiter")
    public String getWaiterPage(){
        return "waiter";
    }
}
