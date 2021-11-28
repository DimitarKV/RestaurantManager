package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.model.binding.UpdateProfileBindingModel;
import com.dim.RestaurantManager.service.UserService;
import com.dim.RestaurantManager.service.impl.RestaurantUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ProfileController {
    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("bindingModel")
    public UpdateProfileBindingModel bindingModel(@AuthenticationPrincipal RestaurantUser user){
        return userService.getUserProfile(user);
    }

    @ModelAttribute(name = "passwordsMatch")
    public boolean getPasswordsMatch() {
        return true;
    }

    @ModelAttribute(name = "usernameAlreadyExists")
    public boolean usernameAlreadyExists() {
        return false;
    }


    @GetMapping("/users/profile")
    public String getProfilePage(){
        return "profile";
    }
}
