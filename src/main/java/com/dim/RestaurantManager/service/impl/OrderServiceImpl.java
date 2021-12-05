package com.dim.RestaurantManager.service.impl;

import com.dim.RestaurantManager.model.entity.*;
import com.dim.RestaurantManager.model.entity.enums.OrderStatusEnum;
import com.dim.RestaurantManager.model.view.CookOrderView;
import com.dim.RestaurantManager.model.view.OrderView;
import com.dim.RestaurantManager.model.view.WaiterOrderView;
import com.dim.RestaurantManager.repository.*;
import com.dim.RestaurantManager.service.OrderService;
import com.dim.RestaurantManager.service.exceptions.common.CommonErrorMessages;
import com.dim.RestaurantManager.utils.components.ClassMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final ClassMapper classMapper;
    private final BillRepository billRepository;
    private final TableRepository tableRepository;

    public OrderServiceImpl(UserRepository userRepository, ItemRepository itemRepository, OrderRepository orderRepository, OrderStatusRepository orderStatusRepository, ClassMapper classMapper, BillRepository billRepository, TableRepository tableRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.classMapper = classMapper;
        this.billRepository = billRepository;
        this.tableRepository = tableRepository;
    }

    @Override
    public Long order(Long itemId, String notes, RestaurantUser restaurantUser) {
        User user = userRepository.findByUsername(restaurantUser.getUsername())
                .orElseThrow(() -> CommonErrorMessages.username(restaurantUser.getUsername()));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> CommonErrorMessages.item(itemId));
        Order order =
                new Order()
                        .setItem(item)
                        .setBill(user.getBill())
                        .setStatus(
                                orderStatusRepository
                                        .findByName(OrderStatusEnum.PENDING)
                                        .get()
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
    public List<CookOrderView> getCurrentCookOrders(RestaurantUser restaurantUser) {
        User user = userRepository.findByUsername(restaurantUser.getUsername())
                .orElseThrow(() -> CommonErrorMessages.username(restaurantUser.getUsername()));

        return classMapper.toCookOrderView(orderRepository.findCurrentCookOrders(user.getId()));
    }

    @Override
    public boolean isOwner(Long orderId, RestaurantUser restaurantUser) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> CommonErrorMessages.order(orderId));
        User user = userRepository.findByUsername(restaurantUser.getUsername())
                .orElseThrow(() -> CommonErrorMessages.username(restaurantUser.getUsername()));
        return user.getBill().getId().equals(order.getBill().getId());
    }

    @Override
    public List<WaiterOrderView> getReadyOrders() {
        return classMapper.toWaiterOrderView(orderRepository.findReadyOrders());
    }

    @Override
    public List<WaiterOrderView> getCurrentWaiterOrders(RestaurantUser restaurantUser) {
        User user = userRepository.findByUsername(restaurantUser.getUsername())
                .orElseThrow(() -> CommonErrorMessages.username(restaurantUser.getUsername()));

        return classMapper.toWaiterOrderView(orderRepository.findCurrentWaiterOrders(user.getId()));
    }

    @Override
    public void init() {
        if (orderRepository.count() == 0) {
            User user = userRepository.findByUsername("customer").get();

            FoodTable table = tableRepository.findByNumber(1).get();

            Bill bill = new Bill()
                    .setUsers(List.of(user))
                    .setTable(table);
            bill = billRepository.saveAndFlush(bill);

            table.setBill(bill);
            tableRepository.saveAndFlush(table);

            user.setBill(bill);
            user = userRepository.saveAndFlush(user);

            Item item1 = itemRepository.findById(1L).get();
            Order order1 =
                    new Order()
                            .setItem(item1)
                            .setBill(bill)
                            .setStatus(
                                    orderStatusRepository
                                            .findByName(OrderStatusEnum.PENDING)
                                            .get()
                            )
                            .setNotes("С почвече чедър!")
                            .setPlaced(LocalDateTime.now());
            Item item2 = itemRepository.findById(2L).get();
            Order order2 =
                    new Order()
                            .setItem(item2)
                            .setBill(bill)
                            .setStatus(
                                    orderStatusRepository
                                            .findByName(OrderStatusEnum.PENDING)
                                            .get()
                            )
                            .setNotes("Без лук!")
                            .setPlaced(LocalDateTime.now());
            Item item3 = itemRepository.findById(3L).get();
            Order order3 =
                    new Order()
                            .setItem(item3)
                            .setBill(bill)
                            .setStatus(
                                    orderStatusRepository
                                            .findByName(OrderStatusEnum.PENDING)
                                            .get()
                            )
                            .setNotes("Без кисели краставички!")
                            .setPlaced(LocalDateTime.now());
            orderRepository.saveAllAndFlush(List.of(order1, order2, order3));

            bill.setOrders(List.of(
                    orderRepository.findById(1L).get(),
                    orderRepository.findById(2L).get(),
                    orderRepository.findById(3L).get()
            ));
            billRepository.saveAndFlush(bill);
        }
    }
}
