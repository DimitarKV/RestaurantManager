package com.dim.RestaurantManager.service;

import com.dim.RestaurantManager.model.service.ManagerAddItemServiceModel;

public interface ItemService {
    void addItem(ManagerAddItemServiceModel toManagerAddItemServiceModel);

    void init();

    void deleteItem(Long itemId);
}
