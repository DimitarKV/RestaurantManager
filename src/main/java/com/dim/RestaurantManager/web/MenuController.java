package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.model.view.CategoryView;
import com.dim.RestaurantManager.service.MenuService;
import com.dim.RestaurantManager.service.OrderService;
import com.dim.RestaurantManager.service.impl.RestaurantUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MenuController {
    private final MenuService menuService;
    private final OrderService orderService;

    public MenuController(MenuService menuService, OrderService orderService) {
        this.menuService = menuService;
        this.orderService = orderService;
    }

    @ModelAttribute(name = "categories")
    public List<CategoryView> menuView() {
        return menuService.getMenuView();
    }

    @ModelAttribute(name = "categoryAlreadyExists")
    public boolean categoryAlreadyExists() {
        return false;
    }

    @ModelAttribute(name = "categoryDoesNotExist")
    public boolean categoryDoesNotExist() {
        return false;
    }

    @GetMapping("/menu")
    public String getMenuPage() {
        return "menu";
    }

    @PreAuthorize("canOrder()")
    @PostMapping("/menu/order")
    public String foodOrder(@ModelAttribute(name = "itemId") String itemId,
                            @ModelAttribute(name = "notes") String notes,
                            @AuthenticationPrincipal RestaurantUser restaurantUser) {
        orderService.order(Long.valueOf(itemId), notes, restaurantUser);
        return "redirect:/menu";
    }

}

