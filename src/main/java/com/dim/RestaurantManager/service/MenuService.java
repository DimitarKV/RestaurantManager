package com.dim.RestaurantManager.service;

import com.dim.RestaurantManager.model.view.MenuView;
import org.springframework.stereotype.Service;

public interface MenuService {
    void init();

    MenuView getMenuView();
}
