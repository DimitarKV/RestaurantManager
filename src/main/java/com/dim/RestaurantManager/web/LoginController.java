package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.model.binding.LoginBindingModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    @ModelAttribute(name = "bindingModel")
    public LoginBindingModel bindingModel() {
        return new LoginBindingModel();
    }

    @ModelAttribute(name = "error")
    public boolean error() {
        return false;
    }

    @GetMapping("/user/login")
    public String getLoginPage() {
        return "auth/login";
    }

    @PostMapping("/user/login-error")
    public String getLoginErrorPage(LoginBindingModel bindingModel,
                                    RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("bindingModel", bindingModel);
        redirectAttributes.addFlashAttribute("error", true);
        return "redirect:/user/login";
    }

}