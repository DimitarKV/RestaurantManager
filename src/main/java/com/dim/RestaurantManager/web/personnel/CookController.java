package com.dim.RestaurantManager.web.personnel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CookController {

    @GetMapping("/personnel/cook")
    public String getCookPage(){
        return "/cook-orders";
    }
}
