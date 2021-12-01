package com.dim.RestaurantManager.utils.components.impl;

import com.dim.RestaurantManager.model.binding.UpdateProfileBindingModel;
import com.dim.RestaurantManager.model.entity.Order;
import com.dim.RestaurantManager.model.entity.Role;
import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.model.entity.enums.RoleEnum;
import com.dim.RestaurantManager.model.service.UpdateProfileServiceModel;
import com.dim.RestaurantManager.model.view.CookOrderView;
import com.dim.RestaurantManager.model.view.ItemView;
import com.dim.RestaurantManager.model.view.OrderView;
import com.dim.RestaurantManager.model.view.UserView;
import com.dim.RestaurantManager.repository.RoleRepository;
import com.dim.RestaurantManager.service.exceptions.EntityNotFoundException;
import com.dim.RestaurantManager.utils.components.ClassMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClassMapperImpl implements ClassMapper {
    private final RoleRepository roleRepository;

    public ClassMapperImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public UpdateProfileBindingModel toUpdateProfileBindingModel(User user) {
        return new UpdateProfileBindingModel()
                .setUsername(user.getUsername())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setAge(user.getAge());
    }

    @Override
    public Role toRole(RoleEnum roleEnum) {
        return this.roleRepository
                .findByRole(roleEnum)
                .orElseThrow(() -> new EntityNotFoundException("Role with name: " + roleEnum + " not found!"));
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
}
