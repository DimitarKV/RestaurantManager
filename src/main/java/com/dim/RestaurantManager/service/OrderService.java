package com.dim.RestaurantManager.service;

import com.dim.RestaurantManager.service.exceptions.EntityNotFoundException;
import com.dim.RestaurantManager.service.impl.RestaurantUser;

public interface OrderService {
    void order(Long itemId, RestaurantUser restaurantUser) throws EntityNotFoundException;
}
