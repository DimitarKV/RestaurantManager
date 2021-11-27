package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.model.entity.enums.RoleEnum;
import com.dim.RestaurantManager.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;

@Controller
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute(name = "users")
    public List<PatchUserBindingModel> userVewList(){
        return userService.getAllUsers();
    }

    @ModelAttribute(name = "roles")
    public RoleEnum[] getRoles(){
        return RoleEnum.values();
    }

    @PreAuthorize("isAdmin()")
    @GetMapping("/users/admin")
    public String getAdminPage(){
        return "admin";
    }

    @PreAuthorize("isAdmin()")
    @PatchMapping("/users/patch-roles")
    public String patchUser(PatchUserBindingModel bindingModel) {
        System.out.println();
        return "redirect:/users/admin";
    }
}
