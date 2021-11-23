package com.dim.RestaurantManager.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/users/login")
    public String getLoginPage() {
        return "auth/login";
    }

    @GetMapping("/users/login-error")
    public String getLoginErrorPage() {
        return "auth/login-error";
    }

}