package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.model.view.MenuView;
import com.dim.RestaurantManager.service.MenuService;
import com.dim.RestaurantManager.service.OrderService;
import com.dim.RestaurantManager.service.exceptions.EntityNotFoundException;
import com.dim.RestaurantManager.service.impl.RestaurantUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MenuController {
    private final MenuService menuService;
    private final OrderService orderService;

    public MenuController(MenuService menuService, OrderService orderService) {
        this.menuService = menuService;
        this.orderService = orderService;
    }

    @ModelAttribute(name = "menu")
    public MenuView menuView() {
        return menuService.getMenuView();
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

