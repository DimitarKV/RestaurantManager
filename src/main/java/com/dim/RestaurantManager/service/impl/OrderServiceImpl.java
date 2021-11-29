package com.dim.RestaurantManager.service.impl;

import com.dim.RestaurantManager.model.entity.Item;
import com.dim.RestaurantManager.model.entity.Order;
import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.model.entity.enums.OrderStatusEnum;
import com.dim.RestaurantManager.model.view.CookOrderView;
import com.dim.RestaurantManager.repository.ItemRepository;
import com.dim.RestaurantManager.repository.OrderRepository;
import com.dim.RestaurantManager.repository.OrderStatusRepository;
import com.dim.RestaurantManager.repository.UserRepository;
import com.dim.RestaurantManager.service.OrderService;
import com.dim.RestaurantManager.service.exceptions.EntityNotFoundException;
import com.dim.RestaurantManager.utils.components.ClassMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final ClassMapper classMapper;

    public OrderServiceImpl(UserRepository userRepository, ItemRepository itemRepository, OrderRepository orderRepository, OrderStatusRepository orderStatusRepository, ClassMapper classMapper) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.classMapper = classMapper;
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

    @Override
    public List<CookOrderView> getPendingOrders() {
        return classMapper.toCookOrderView(orderRepository.getPendingOrders());
    }
}
