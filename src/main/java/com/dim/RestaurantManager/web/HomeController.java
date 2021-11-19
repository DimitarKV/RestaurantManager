package com.dim.RestaurantManager.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String serveHomePage(){
        return "index";
    }
}
