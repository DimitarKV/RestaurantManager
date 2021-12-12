package com.dim.RestaurantManager.service;

import com.dim.RestaurantManager.model.binding.ManagerEditItemBindingModel;
import com.dim.RestaurantManager.model.service.ManagerAddItemServiceModel;
import com.dim.RestaurantManager.model.service.ManagerEditItemServiceModel;
import com.dim.RestaurantManager.model.view.CategoryView;

import java.util.List;

public interface MenuService {
    List<CategoryView> getMenuView();

    void addMenuItem(ManagerAddItemServiceModel toManagerAddItemServiceModel);

    ManagerEditItemBindingModel getEditMenuItem(Long itemId);

    void deleteMenuItem(Long itemId);

    void editItem(ManagerEditItemServiceModel serviceModel);

    void init();
}
