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

    UpdateProfileBindingModel getUserProfile(RestaurantUser user);

    void updateUserProfile(String username, UpdateProfileServiceModel updateProfileServiceModel);

    void acceptCookOrder(RestaurantUser user, Long orderId);

    void readyCookOrder(Long orderId);

    void cancelCookOrder(Long orderId);

    void acceptWaiterOrder(RestaurantUser user, Long orderId);

    void finishWaiterOrder(Long orderId);

    void cancelWaiterOrder(Long orderId);

    void cancelUserOrder(Long orderId);

    boolean hasNotOccupied(String username);

    List<UserView> getUsers(Integer pageSize, Integer offset);

    Integer getUsersPageCount(Integer pageSize);

    boolean isAdmin(String username);

    boolean isCook(String username);

    boolean isWaiter(String username);
}
