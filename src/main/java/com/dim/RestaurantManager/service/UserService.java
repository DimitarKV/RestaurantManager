package com.dim.RestaurantManager.service;

import com.dim.RestaurantManager.model.binding.UpdateProfileBindingModel;
import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.model.service.RegisterServiceModel;
import com.dim.RestaurantManager.model.service.UpdateProfileServiceModel;
import com.dim.RestaurantManager.model.view.CookOrderView;
import com.dim.RestaurantManager.model.view.OrderView;
import com.dim.RestaurantManager.model.view.UserView;
import com.dim.RestaurantManager.service.impl.RestaurantUser;
import com.dim.RestaurantManager.model.binding.ModifyUserRolesBindingModel;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean registerAndLoginUser(RegisterServiceModel serviceModel);

    Optional<User> getByUsername(String username);

    void init();

    boolean usernameExists(String username);

    Integer findTableNumberByUsername(String username);

    void updatePrincipal();

    List<OrderView> getOrders(RestaurantUser restaurantUser);

    List<UserView> getAllUsers();

    void modifyUserRoles(ModifyUserRolesBindingModel bindingModel);

    boolean isAdmin(String username);

    UpdateProfileBindingModel getUserProfile(RestaurantUser user);

    void updateUserProfile(String username, UpdateProfileServiceModel updateProfileServiceModel);

    void acceptOrder(RestaurantUser user, Long orderId);

    void readyOrder(Long orderId);

    void cancelCookOrder(Long orderId);

    void cancelUserOrder(Long orderId);

    boolean hasNotOccupied(String username);
}
