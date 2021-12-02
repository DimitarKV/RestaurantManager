package com.dim.RestaurantManager.service.exceptions.common;

import com.dim.RestaurantManager.service.exceptions.EntityNotFoundException;

public class CommonErrorMessages {
    public static EntityNotFoundException username(String username){
        return new EntityNotFoundException("User with username: " + username + " not found!");
    }
    public static EntityNotFoundException userId(Long userId){
        return new EntityNotFoundException("User with userId: " + userId + " not found!");
    }
    public static EntityNotFoundException item(Long itemId){
        return new EntityNotFoundException("Item with id: " + itemId + " not found!");
    }
    public static EntityNotFoundException order(Long orderId){
        return new EntityNotFoundException("Order with id: " + orderId + " not found!");
    }

    public static EntityNotFoundException table(Integer tableNumber){
        return new EntityNotFoundException("Table with number: " + tableNumber + " not found!");
    }

    public static RuntimeException notLogged() {
        return new RuntimeException("You are not logged in!");
    }
}
