package com.dim.RestaurantManager.service;

import com.dim.RestaurantManager.model.binding.UpdateProfileBindingModel;
import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.model.service.RegisterServiceModel;
import com.dim.RestaurantManager.model.view.OrderView;
import com.dim.RestaurantManager.model.view.UserView;
import com.dim.RestaurantManager.service.impl.RestaurantUser;
import com.dim.RestaurantManager.web.ModifyUserRolesBindingModel;

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
}
