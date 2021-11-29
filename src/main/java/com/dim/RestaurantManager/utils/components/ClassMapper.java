package com.dim.RestaurantManager.utils.components;

import com.dim.RestaurantManager.model.binding.UpdateProfileBindingModel;
import com.dim.RestaurantManager.model.entity.Order;
import com.dim.RestaurantManager.model.entity.Role;
import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.model.entity.enums.RoleEnum;
import com.dim.RestaurantManager.model.service.UpdateProfileServiceModel;
import com.dim.RestaurantManager.model.view.OrderView;
import com.dim.RestaurantManager.model.view.UserView;

import java.util.List;

public interface ClassMapper {
    UpdateProfileBindingModel toUpdateProfileBindingModel(User user);
    Role toRole(RoleEnum roleEnum);
    UserView toUserView(User user);
    List<OrderView> toListOrderView(List<Order> orders);
    OrderView toOrderView(Order order);
    UpdateProfileServiceModel toUpdateProfileServiceModel(UpdateProfileBindingModel bindingModel);
}
