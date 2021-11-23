package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.model.view.MenuView;
import com.dim.RestaurantManager.service.MenuService;
import com.dim.RestaurantManager.service.OrderService;
import com.dim.RestaurantManager.service.exceptions.EntityNotFoundException;
import com.dim.RestaurantManager.service.impl.RestaurantUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MenuController {
    private final MenuService menuService;
    private final OrderService orderService;

    public MenuController(MenuService menuService, OrderService orderService) {
        this.menuService = menuService;
        this.orderService = orderService;
    }

    @ModelAttribute(name = "menu")
    public MenuView menuView(){ return menuService.getMenuView(); }

    @GetMapping("/menu")
    public String getMenuPage(){
        return "menu";
    }

    @PostMapping("/menu/order")
    public String foodOrder(@ModelAttribute(name = "itemId") Long itemId,
                            @AuthenticationPrincipal RestaurantUser restaurantUser) throws EntityNotFoundException {
        orderService.order(itemId, restaurantUser);
        return "redirect:/menu";
    }

}

