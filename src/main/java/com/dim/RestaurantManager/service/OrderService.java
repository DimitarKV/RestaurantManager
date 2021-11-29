package com.dim.RestaurantManager.service;

import com.dim.RestaurantManager.model.view.CookOrderView;
import com.dim.RestaurantManager.service.exceptions.EntityNotFoundException;
import com.dim.RestaurantManager.service.impl.RestaurantUser;

import java.util.List;

public interface OrderService {
    void order(Long itemId, RestaurantUser restaurantUser) throws EntityNotFoundException;

    List<CookOrderView> getPendingOrders();
}
