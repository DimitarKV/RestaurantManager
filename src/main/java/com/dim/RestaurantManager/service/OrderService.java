package com.dim.RestaurantManager.service;

import com.dim.RestaurantManager.model.view.CookOrderView;
import com.dim.RestaurantManager.model.view.OrderView;
import com.dim.RestaurantManager.model.view.WaiterOrderView;
import com.dim.RestaurantManager.service.exceptions.EntityNotFoundException;
import com.dim.RestaurantManager.service.impl.RestaurantUser;

import java.util.List;

public interface OrderService {
    Long order(Long itemId, String notes, RestaurantUser restaurantUser) throws EntityNotFoundException;

    void init();

    List<OrderView> getOrders(RestaurantUser restaurantUser);

    boolean isOwner(Long orderId, RestaurantUser restaurantUser);

    void acceptWaiterOrder(RestaurantUser user, Long orderId);

    List<WaiterOrderView> getReadyOrders();

    List<WaiterOrderView> getCurrentWaiterOrders(RestaurantUser restaurantUser);

    void cancelWaiterOrder(Long orderId);

    void finishWaiterOrder(Long orderId);

    void acceptCookOrder(RestaurantUser user, Long orderId);

    List<CookOrderView> getPendingOrders();

    List<CookOrderView> getCurrentCookOrders(RestaurantUser restaurantUser);

    void readyCookOrder(Long orderId);

    void cancelCookOrder(Long orderId);

    void cancelUserOrder(Long orderId);
}
