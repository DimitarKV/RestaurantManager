package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.model.view.UserView;
import com.dim.RestaurantManager.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute(name = "users")
    public List<UserView> userVewList(){
        return userService.getAllUsers();
    }

    @PreAuthorize("isAdmin()")
    @GetMapping("/users/admin")
    public String getAdminPage(){
        return "admin";
    }
}
