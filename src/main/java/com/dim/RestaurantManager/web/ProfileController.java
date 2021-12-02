package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.model.binding.UpdateProfileBindingModel;
import com.dim.RestaurantManager.model.service.UpdateProfileServiceModel;
import com.dim.RestaurantManager.service.UserService;
import com.dim.RestaurantManager.service.impl.RestaurantUser;
import com.dim.RestaurantManager.utils.components.ClassMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ProfileController {
    private final UserService userService;
    private final ClassMapper classMapper;

    public ProfileController(UserService userService, ClassMapper classMapper) {
        this.userService = userService;
        this.classMapper = classMapper;
    }

    @ModelAttribute("bindingModel")
    public UpdateProfileBindingModel bindingModel(@AuthenticationPrincipal RestaurantUser user) {
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


    @GetMapping("/user/profile")
    public String getProfilePage() {
        return "profile";
    }


    @PatchMapping("/user/profile")
    public String updateProfile(@AuthenticationPrincipal RestaurantUser user,
                                @Valid UpdateProfileBindingModel bindingModel,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() ||
                !bindingModel.getPassword().equals(bindingModel.getRepeatPassword()) ||
                (!SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName()
                        .equals(bindingModel.getUsername()) &&
                        this.userService.usernameExists(bindingModel.getUsername()))
        ) {
            if (!bindingModel.getPassword().equals(bindingModel.getRepeatPassword())) {
                redirectAttributes.addFlashAttribute("passwordsMatch", false);
            }
            if (!SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName()
                    .equals(bindingModel.getUsername()) &&
                    this.userService.usernameExists(bindingModel.getUsername())) {
                redirectAttributes.addFlashAttribute("usernameAlreadyExists", false);
            }
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.bindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("bindingModel", bindingModel);
            return "redirect:/user/profile";
        }

        UpdateProfileServiceModel updateProfileServiceModel = classMapper.toUpdateProfileServiceModel(bindingModel);

        userService.updateUserProfile(user.getUsername(), updateProfileServiceModel);

        return "redirect:/user/profile";
    }
}
