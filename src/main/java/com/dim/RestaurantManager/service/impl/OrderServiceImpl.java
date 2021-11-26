package com.dim.RestaurantManager.service.impl;

import com.dim.RestaurantManager.model.entity.Item;
import com.dim.RestaurantManager.model.entity.Order;
import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.model.entity.enums.OrderStatusEnum;
import com.dim.RestaurantManager.repository.ItemRepository;
import com.dim.RestaurantManager.repository.OrderRepository;
import com.dim.RestaurantManager.repository.OrderStatusRepository;
import com.dim.RestaurantManager.repository.UserRepository;
import com.dim.RestaurantManager.service.OrderService;
import com.dim.RestaurantManager.service.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;

    public OrderServiceImpl(UserRepository userRepository, ItemRepository itemRepository, OrderRepository orderRepository, OrderStatusRepository orderStatusRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public void order(Long itemId, RestaurantUser restaurantUser) throws EntityNotFoundException {
        User user = userRepository.findByUsername(restaurantUser.getUsername()).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new EntityNotFoundException("Item not found!"));
        Order order =
                new Order()
                        .setItem(item)
                        .setBill(user.getBill())
                        .setStatus(
                                orderStatusRepository
                                        .findByName(OrderStatusEnum.PENDING)
                                        .orElseThrow(() -> new EntityNotFoundException("Order status not found!"))
                        )
                        .setPlaced(LocalDateTime.now());
        orderRepository.saveAndFlush(order);

    }
}
