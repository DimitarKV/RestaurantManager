package com.dim.RestaurantManager.service.impl;

import com.dim.RestaurantManager.model.entity.Bill;
import com.dim.RestaurantManager.model.entity.Item;
import com.dim.RestaurantManager.model.entity.Order;
import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.model.entity.enums.OrderStatusEnum;
import com.dim.RestaurantManager.model.view.CookOrderView;
import com.dim.RestaurantManager.repository.*;
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
    private final BillRepository billRepository;

    public OrderServiceImpl(UserRepository userRepository, ItemRepository itemRepository, OrderRepository orderRepository, OrderStatusRepository orderStatusRepository, ClassMapper classMapper, BillRepository billRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.classMapper = classMapper;
        this.billRepository = billRepository;
    }

    @Override
    public Long order(Long itemId, String notes, RestaurantUser restaurantUser) throws EntityNotFoundException {
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
                        .setNotes(notes)
                        .setPlaced(LocalDateTime.now());
        order = orderRepository.saveAndFlush(order);
        return order.getId();
    }

    @Override
    public List<CookOrderView> getPendingOrders() {
        return classMapper.toCookOrderView(orderRepository.getPendingOrders());
    }

    @Override
    public void init() {
        if(orderRepository.count() == 0){
            User user = userRepository.findByUsername("mitko").orElseThrow(() -> new EntityNotFoundException("User not found!"));
            Bill bill = new Bill()
                    .setUsers(List.of(user));
            bill = billRepository.saveAndFlush(bill);
            user.setBill(bill);
            Item item1 = itemRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Item not found!"));
            Order order1 =
                    new Order()
                            .setItem(item1)
                            .setBill(user.getBill())
                            .setStatus(
                                    orderStatusRepository
                                            .findByName(OrderStatusEnum.PENDING)
                                            .orElseThrow(() -> new EntityNotFoundException("Order status not found!"))
                            )
                            .setNotes("С почвече чедър!")
                            .setPlaced(LocalDateTime.now());
            Item item2 = itemRepository.findById(2L).orElseThrow(() -> new EntityNotFoundException("Item not found!"));
            Order order2 =
                    new Order()
                            .setItem(item2)
                            .setBill(user.getBill())
                            .setStatus(
                                    orderStatusRepository
                                            .findByName(OrderStatusEnum.PENDING)
                                            .orElseThrow(() -> new EntityNotFoundException("Order status not found!"))
                            )
                            .setNotes("Без лук!")
                            .setPlaced(LocalDateTime.now());
            Item item3 = itemRepository.findById(3L).orElseThrow(() -> new EntityNotFoundException("Item not found!"));
            Order order3 =
                    new Order()
                            .setItem(item3)
                            .setBill(user.getBill())
                            .setStatus(
                                    orderStatusRepository
                                            .findByName(OrderStatusEnum.PENDING)
                                            .orElseThrow(() -> new EntityNotFoundException("Order status not found!"))
                            )
                            .setNotes("Без кисели краставички!")
                            .setPlaced(LocalDateTime.now());
            orderRepository.saveAllAndFlush(List.of(order1, order2, order3));
        }
    }
}
