package com.dim.RestaurantManager.service;

import com.dim.RestaurantManager.model.view.CategoryView;

import java.util.List;

public interface MenuService {
    void init();

    List<CategoryView> getMenuView();
}
