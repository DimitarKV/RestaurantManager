package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.model.binding.RegisterBindingModel;
import com.dim.RestaurantManager.model.service.RegisterServiceModel;
import com.dim.RestaurantManager.service.UserService;
import com.dim.RestaurantManager.web.rest.responses.UsernameAvailabilityResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute(name = "bindingModel")
    public RegisterBindingModel getBindingModel() {
        return new RegisterBindingModel();
    }

    @ModelAttribute(name = "passwordsMatch")
    public boolean getPasswordsMatch() {
        return true;
    }

    @ModelAttribute(name = "usernameAlreadyExists")
    public boolean usernameAlreadyExists() {
        return false;
    }


    @GetMapping("/users/register")
    public String getRegisterPage() {
        return "auth/register";
    }

    @PostMapping("/users/register")
    public String register(@Valid RegisterBindingModel bindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() ||
                !bindingModel.getPassword().equals(bindingModel.getRepeatPassword()) ||
                this.userService.usernameExists(bindingModel.getUsername())) {
            if (this.userService.usernameExists(bindingModel.getUsername())) {
                redirectAttributes.addFlashAttribute("usernameAlreadyExists", true);
            }
            if (!bindingModel.getPassword().equals(bindingModel.getRepeatPassword())) {
                redirectAttributes.addFlashAttribute("passwordsMatch", false);
            }
            redirectAttributes.addFlashAttribute("bindingModel", bindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.bindingModel", bindingResult);
            return "redirect:/users/register";
        }

        RegisterServiceModel serviceModel = new RegisterServiceModel()
                .setUsername(bindingModel.getUsername())
                .setPassword(bindingModel.getPassword());
        if (!this.userService.registerAndLoginUser(serviceModel)) {
            return "redirect:/users/register";
        }

        return "redirect:/";
    }

    // NOTE: This feature is for demonstration purposes only.
    // In a production app without additional security this is considered to be a security breach.
    @GetMapping("/users/register/check/{username}")
    @ResponseBody
    public ResponseEntity usernameAvailable(@PathVariable(name = "username") String username) throws InterruptedException {
        Thread.sleep(1000);
        if (this.userService.usernameExists(username))
            return ResponseEntity.ok(new UsernameAvailabilityResponse().setAvailable(false));
        return ResponseEntity.ok(new UsernameAvailabilityResponse().setAvailable(true));
    }
}
