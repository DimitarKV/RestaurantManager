package com.dim.RestaurantManager.utils.components.impl;

import com.dim.RestaurantManager.model.binding.UpdateProfileBindingModel;
import com.dim.RestaurantManager.model.entity.Order;
import com.dim.RestaurantManager.model.entity.Role;
import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.model.entity.enums.RoleEnum;
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
    public UpdateProfileBindingModel mapToUpdateProfileBindingModel(User user) {
        return new UpdateProfileBindingModel()
                .setUsername(user.getUsername())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setAge(user.getAge());
    }

    @Override
    public Role mapRoleEnumToRole(RoleEnum roleEnum) {
        return this.roleRepository
                .findByRole(roleEnum)
                .orElseThrow(() -> new EntityNotFoundException("Role with name: " + roleEnum + " not found!"));
    }

    @Override
    public UserView mapToUserView(User user) {
        return new UserView()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setAge(user.getAge())
                .setRoles(user.getRoles().stream().map(Role::getRole).collect(Collectors.toList()));

    }

    @Override
    public List<OrderView> mapToListOrderView(List<Order> orders) {
        return orders.stream().map(this::mapToOrderView).collect(Collectors.toList());
    }

    @Override
    public OrderView mapToOrderView(Order order) {
        return new OrderView()
                .setItemView(new ItemView()
                        .setId(order.getItem().getId())
                        .setName(order.getItem().getName())
                        .setDescription(order.getItem().getDescription())
                        .setImageUrl(order.getItem().getImageUrl())
                        .setPrice(order.getItem().getPrice())
                )
                .setOrderStatus(order.getStatus().getName());
    }
}
