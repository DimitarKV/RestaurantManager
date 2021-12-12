package com.dim.RestaurantManager.utils.components;

import com.dim.RestaurantManager.model.binding.ManagerAddItemBindingModel;
import com.dim.RestaurantManager.model.binding.ManagerEditItemBindingModel;
import com.dim.RestaurantManager.model.binding.UpdateProfileBindingModel;
import com.dim.RestaurantManager.model.entity.*;
import com.dim.RestaurantManager.model.entity.enums.RoleEnum;
import com.dim.RestaurantManager.model.service.ManagerAddItemServiceModel;
import com.dim.RestaurantManager.model.service.ManagerEditItemServiceModel;
import com.dim.RestaurantManager.model.service.UpdateProfileServiceModel;
import com.dim.RestaurantManager.model.view.*;

import java.util.List;

public interface ClassMapper {
    UpdateProfileBindingModel toUpdateProfileBindingModel(User user);

    UserView toUserView(User user);

    List<OrderView> toListOrderView(List<Order> orders);

    OrderView toOrderView(Order order);

    UpdateProfileServiceModel toUpdateProfileServiceModel(UpdateProfileBindingModel bindingModel);

    List<CookOrderView> toCookOrderView(List<Order> pendingOrders);

    List<WaiterOrderView> toWaiterOrderView(List<Order> order);

    CheckoutOrderView toCheckoutOrderView(Order order);

    FoodTableView toFoodTableView(FoodTable foodTable);

    List<CategoryView> toCategoryViewList(List<Category> categories, List<MenuItem> items);

    ItemView toItemView(MenuItem item);

    ManagerAddItemServiceModel toManagerAddItemServiceModel(ManagerAddItemBindingModel bindingModel);

    ManagerEditItemBindingModel toManagerEditItemBindingModel(MenuItem menuItem);

    ManagerEditItemServiceModel toManagerEditItemServiceModel(ManagerEditItemBindingModel bindingModel, Long id);
}
