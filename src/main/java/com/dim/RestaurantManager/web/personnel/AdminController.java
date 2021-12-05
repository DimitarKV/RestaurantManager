package com.dim.RestaurantManager.web.personnel;

import com.dim.RestaurantManager.model.binding.ModifyUserRolesBindingModel;
import com.dim.RestaurantManager.model.entity.enums.RoleEnum;
import com.dim.RestaurantManager.model.view.UserView;
import com.dim.RestaurantManager.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {
    private final UserService userService;
    private final Integer PAGE_SIZE = 15;
    private final Integer PAGES_IN_NAV = 5;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute(name = "pageCount")
    public Integer getPageCount() {
        return userService.getUsersPageCount(PAGE_SIZE);
    }

    @ModelAttribute(name = "pagesInNav")
    public Integer getPagesInNav() {
        return PAGES_IN_NAV;
    }

    @ModelAttribute(name = "roles")
    public RoleEnum[] getRoles(){
        return RoleEnum.values();
    }


    @PreAuthorize("isAdmin()")
    @GetMapping("/personnel/admin")
    public String getAdminPage(Model model) {
        model.addAttribute("pageNumber", 1);
        model.addAttribute("users", userService.getUsers(PAGE_SIZE, 0));
        return "admin";
    }

    @PreAuthorize("isAdmin()")
    @GetMapping("/personnel/{pageNumber}/admin")
    public String getAdminNthPage(@PathVariable("pageNumber") Integer pageNumber,
                                  Model model) {
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("users", userService.getUsers(PAGE_SIZE, (pageNumber - 1) * PAGE_SIZE));
        return "admin";
    }

    @PreAuthorize("isAdmin()")
    @PatchMapping("/personnel/patch-roles")
    public String patchUser(ModifyUserRolesBindingModel bindingModel,
                            @RequestParam(name = "role", required = false) List<RoleEnum> roles) {
        if (roles != null)
            bindingModel.setRoles(roles);
        userService.modifyUserRoles(bindingModel);
        return "redirect:/personnel/admin";
    }
}
