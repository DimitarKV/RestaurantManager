package com.dim.RestaurantManager.utils.components;

import com.dim.RestaurantManager.model.binding.UpdateProfileBindingModel;
import com.dim.RestaurantManager.model.entity.Order;
import com.dim.RestaurantManager.model.entity.Role;
import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.model.entity.enums.RoleEnum;
import com.dim.RestaurantManager.model.view.OrderView;
import com.dim.RestaurantManager.model.view.UserView;

import java.util.List;

public interface ClassMapper {
    UpdateProfileBindingModel mapToUpdateProfileBindingModel(User user);
    Role mapRoleEnumToRole(RoleEnum roleEnum);
    UserView mapToUserView(User user);
    List<OrderView> mapToListOrderView(List<Order> orders);
    OrderView mapToOrderView(Order order);
}
