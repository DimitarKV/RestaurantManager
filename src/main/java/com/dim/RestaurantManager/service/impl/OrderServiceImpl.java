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
import com.dim.RestaurantManager.model.view.CheckoutOrderView;
import com.dim.RestaurantManager.web.rest.binding.CheckedOrdersBindingModel;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        Bill bill = order.getBill();
        bill.setTotalPrice(bill.getTotalPrice() + order.getItem().getPrice());
        billRepository.saveAndFlush(bill);
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
    public void cancelWaiterOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> CommonErrorMessages.order(orderId));
        if (order.getStatus().getName() != OrderStatusEnum.TRAVELING)
            return;
        order.setStatus(orderStatusRepository.findByName(OrderStatusEnum.READY).get());
        orderRepository.saveAndFlush(order);
    }

    @Override
    public void acceptCookOrder(RestaurantUser restaurantUser, Long orderId) {
        User user = userRepository
                .findByUsername(restaurantUser.getUsername())
                .orElseThrow(() -> CommonErrorMessages.username(restaurantUser.getUsername()));
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> CommonErrorMessages.order(orderId));
        if (order.getStatus().getName() != OrderStatusEnum.PENDING)
            return;
        order.setCook(user);
        order.setStatus(orderStatusRepository.findByName(OrderStatusEnum.COOKING).get());
        orderRepository.saveAndFlush(order);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void readyCookOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> CommonErrorMessages.order(orderId));
        if (order.getStatus().getName() != OrderStatusEnum.COOKING)
            return;
        order
                .setStatus(orderStatusRepository.findByName(OrderStatusEnum.READY).get());
        orderRepository.saveAndFlush(order);
    }

    @Override
    public void cancelCookOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> CommonErrorMessages.order(orderId));
        if (order.getStatus().getName() != OrderStatusEnum.COOKING)
            return;
        order.setStatus(orderStatusRepository.findByName(OrderStatusEnum.PENDING).get());
        orderRepository.saveAndFlush(order);
    }

    @Override
    public void acceptWaiterOrder(RestaurantUser restaurantUser, Long orderId) {
        User user = userRepository
                .findByUsername(restaurantUser.getUsername())
                .orElseThrow(() -> CommonErrorMessages.username(restaurantUser.getUsername()));
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> CommonErrorMessages.order(orderId));

        if (order.getStatus().getName() != OrderStatusEnum.READY)
            return;
        order.setWaiter(user);
        order.setStatus(orderStatusRepository.findByName(OrderStatusEnum.TRAVELING).get());
        orderRepository.saveAndFlush(order);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void finishWaiterOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> CommonErrorMessages.order(orderId));
        if (order.getStatus().getName() != OrderStatusEnum.TRAVELING)
            return;
        order
                .setStatus(orderStatusRepository.findByName(OrderStatusEnum.FINISHED).get());
        orderRepository.saveAndFlush(order);
    }

    @Override
    public void cancelUserOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> CommonErrorMessages.order(orderId));
        if (order.getStatus().getName() != OrderStatusEnum.PENDING)
            return;
        Bill bill = order.getBill();
        bill.setTotalPrice(bill.getTotalPrice());
        orderRepository.delete(order);
    }

    @Override
    public List<CheckoutOrderView> getFinishedOrders(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> CommonErrorMessages.username(username));
        Bill bill = user.getBill();
        return orderRepository
                .findOrdersByBillId(bill.getId())
                .stream()
                .map(classMapper::toCheckoutOrderView).collect(Collectors.toList());
    }

    @Override
    public void handleOrdersByUser(RestaurantUser restaurantUser, CheckedOrdersBindingModel bindingModel) {

    }

    @Override
    public void init() {
        if (orderRepository.count() == 0) {
            User user = userRepository.findByUsername("customer").get();

            FoodTable table = tableRepository.findByNumber(1).get();

            Bill bill = new Bill()
                    .setUsers(List.of(user))
                    .setTable(table)
                    .setCreationDate(LocalDateTime.now());
            bill = billRepository.saveAndFlush(bill);

            table.setBill(bill);
            tableRepository.saveAndFlush(table);

            user.setBill(bill);
            user = userRepository.saveAndFlush(user);

            order(1L, "", new RestaurantUser("customer", "",  List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER")), null));
            order(2L, "", new RestaurantUser("customer", "",  List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER")), null));
            order(3L, "", new RestaurantUser("customer", "",  List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER")), null));
        }
    }

    @Override
    public List<OrderView> getOrders(RestaurantUser restaurantUser) {
        User user = userRepository
                .findByUsername(restaurantUser.getUsername())
                .orElseThrow(() -> CommonErrorMessages.username(restaurantUser.getUsername()));
        if (user.getBill() == null)
            return null;
        return classMapper.toListOrderView(orderRepository.getOrdersByBillId(user.getBill().getId()));
    }
}
