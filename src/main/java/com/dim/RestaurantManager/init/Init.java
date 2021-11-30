package com.dim.RestaurantManager.init;

import com.dim.RestaurantManager.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements CommandLineRunner {
    private final UserService userService;
    private final MenuService menuService;
    private final ItemService itemService;
    private final CategoryService categoryService;
    private final TableService tableService;
    private final OrderStatusService orderStatusService;
    private final OrderService orderService;

    public Init(UserService userService, MenuService menuService, ItemService itemService, CategoryService categoryService, TableService tableService, OrderStatusService orderStatusService, OrderService orderService) {
        this.userService = userService;
        this.menuService = menuService;
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.tableService = tableService;
        this.orderStatusService = orderStatusService;
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userService.init();
        this.categoryService.init();
        this.itemService.init();
        this.menuService.init();
        this.tableService.init();
        this.orderStatusService.init();
        this.orderService.init();
    }
}
