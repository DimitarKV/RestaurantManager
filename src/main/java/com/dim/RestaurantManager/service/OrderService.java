package com.dim.RestaurantManager.service;

import com.dim.RestaurantManager.model.view.CookOrderView;
import com.dim.RestaurantManager.service.exceptions.EntityNotFoundException;
import com.dim.RestaurantManager.service.impl.RestaurantUser;

import java.util.List;

public interface OrderService {
    Long order(Long itemId, String notes, RestaurantUser restaurantUser) throws EntityNotFoundException;

    List<CookOrderView> getPendingOrders();

    void init();

    List<CookOrderView> getCurrentCookOrders(RestaurantUser restaurantUser);

    boolean isOwner(Long orderId, RestaurantUser restaurantUser);
}
