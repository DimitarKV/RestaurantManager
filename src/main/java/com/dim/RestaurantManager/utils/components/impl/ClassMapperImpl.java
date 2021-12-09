package com.dim.RestaurantManager.utils.components.impl;

import com.dim.RestaurantManager.model.binding.UpdateProfileBindingModel;
import com.dim.RestaurantManager.model.entity.FoodTable;
import com.dim.RestaurantManager.model.entity.Order;
import com.dim.RestaurantManager.model.entity.Role;
import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.model.entity.enums.RoleEnum;
import com.dim.RestaurantManager.model.service.UpdateProfileServiceModel;
import com.dim.RestaurantManager.model.view.*;
import com.dim.RestaurantManager.repository.RoleRepository;
import com.dim.RestaurantManager.service.exceptions.EntityNotFoundException;
import com.dim.RestaurantManager.utils.components.ClassMapper;
import com.dim.RestaurantManager.model.view.CheckoutOrderView;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

public class ClassMapperImpl implements ClassMapper {

    @Override
    public UpdateProfileBindingModel toUpdateProfileBindingModel(User user) {
        return new UpdateProfileBindingModel()
                .setUsername(user.getUsername())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setAge(user.getAge());
    }

    @Override
    public UserView toUserView(User user) {
        return new UserView()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setAge(user.getAge())
                .setRoles(user.getRoles().stream().map(Role::getRole).collect(Collectors.toList()));

    }

    @Override
    public List<OrderView> toListOrderView(List<Order> orders) {
        return orders.stream().map(this::toOrderView).collect(Collectors.toList());
    }

    @Override
    public OrderView toOrderView(Order order) {
        return new OrderView()
                .setItemView(new ItemView()
                        .setId(order.getId())
                        .setName(order.getItem().getName())
                        .setDescription(order.getItem().getDescription())
                        .setImageUrl(order.getItem().getImageUrl())
                        .setPrice(order.getItem().getPrice())
                )
                .setOrderStatus(order.getStatus().getName());
    }

    @Override
    public UpdateProfileServiceModel toUpdateProfileServiceModel(UpdateProfileBindingModel bindingModel) {
        return new UpdateProfileServiceModel()
                .setUsername(bindingModel.getUsername())
                .setPassword(bindingModel.getPassword())
                .setFirstName(bindingModel.getFirstName())
                .setLastName(bindingModel.getLastName())
                .setAge(bindingModel.getAge());
    }

    @Override
    public List<CookOrderView> toCookOrderView(List<Order> pendingOrders) {
        return pendingOrders.stream().map(o ->
                        new CookOrderView()
                                .setId(o.getId())
                                .setName(o.getItem().getName())
                                .setDescription(o.getNotes())
                                .setImageUrl(o.getItem().getImageUrl()))
                .collect(Collectors.toList());
    }

    @Override
    public List<WaiterOrderView> toWaiterOrderView(List<Order> orders) {
        return orders.stream().map(o ->
                        new WaiterOrderView()
                                .setId(o.getId())
                                .setName(o.getItem().getName())
                                .setDescription(o.getNotes())
                                .setImageUrl(o.getItem().getImageUrl())
                                .setTableView(this.toFoodTableView(o.getBill().getTable())))
                .collect(Collectors.toList());
    }

    @Override
    public CheckoutOrderView toCheckoutOrderView(Order order) {
        return new CheckoutOrderView()
                .setOrderId(order.getId())
                .setName(order.getItem().getName())
                .setPrice(order.getItem().getPrice())
                .setImageUrl(order.getItem().getImageUrl())
                .setCheckDisabled(
                        order.getPayer() != null &&
                                !order.getPayer()
                                        .getUsername()
                                        .equals(
                                                SecurityContextHolder
                                                        .getContext()
                                                        .getAuthentication()
                                                        .getName()
                                        )
                )
                .setChecked(
                        order.getPayer() != null &&
                                order.getPayer()
                                        .getUsername()
                                        .equals(
                                                SecurityContextHolder
                                                        .getContext()
                                                        .getAuthentication()
                                                        .getName()
                                        )
                )
                .setPayer(order.getPayer() == null ? null : order.getPayer().getUsername());
    }

    @Override
    public FoodTableView toFoodTableView(FoodTable foodTable) {
        return new FoodTableView()
                .setTitle(foodTable.getTitle())
                .setNumber(foodTable.getNumber())
                .setImageUrl(foodTable.getImageUrl())
                .setDescription(foodTable.getDescription());
    }
}
