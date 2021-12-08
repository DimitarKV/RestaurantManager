package com.dim.RestaurantManager.service.impl;

import com.dim.RestaurantManager.model.entity.*;
import com.dim.RestaurantManager.model.entity.enums.OrderStatusEnum;
import com.dim.RestaurantManager.model.view.CookOrderView;
import com.dim.RestaurantManager.model.view.WaiterOrderView;
import com.dim.RestaurantManager.repository.*;
import com.dim.RestaurantManager.utils.components.impl.ClassMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {
    private OrderServiceImpl serviceToTest;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderStatusRepository orderStatusRepository;
    @Mock
    private BillRepository billRepository;
    @Mock
    private TableRepository tableRepository;

    private User testUser;
    private RestaurantUser restaurantUser;
    private Order testOrder, pendingOrder, readyOrder;
    private Item testItem;
    private Bill testBill;
    private FoodTable testTable;

    @BeforeEach
    void init() {
        serviceToTest =
                new OrderServiceImpl(
                        userRepository,
                        itemRepository,
                        orderRepository,
                        orderStatusRepository,
                        new ClassMapperImpl(),
                        billRepository,
                        tableRepository
                );

        testUser = new User()
                .setUsername("mitko")
                .setPassword("mitko")
                .setFirstName("first_name")
                .setLastName("last_name")
                .setAge(17)
                .setRoles(List.of());
        testUser.setId(1L);

        restaurantUser = new RestaurantUser(testUser.getUsername(), testUser.getPassword(), List.of(), userRepository);

        testTable = new FoodTable()
                .setTitle("title")
                .setNumber(1)
                .setDescription("description")
                .setImageUrl("image");
        testTable.setId(1L);

        testItem = new Item()
                .setName("burgir")
                .setPrice(11.9)
                .setImageUrl("image")
                .setDescription("description");

        testBill = new Bill()
                .setTable(testTable);
        testBill.setId(1L);

        testOrder = new Order()
                .setItem(testItem)
                .setBill(testBill)
                .setNotes("notes");
        testOrder.setId(1L);

        pendingOrder = new Order()
                .setItem(testItem)
                .setBill(testBill)
                .setNotes("notes")
                .setStatus(new OrderStatus().setName(OrderStatusEnum.PENDING));
        pendingOrder.setId(2L);

        readyOrder = new Order()
                .setItem(testItem)
                .setBill(testBill)
                .setNotes("notes")
                .setStatus(new OrderStatus().setName(OrderStatusEnum.READY));
        readyOrder.setId(3L);
    }

    @Test
    void testGetCurrentCookOrdersThrows() {
        Assertions.assertThrows(
                RuntimeException.class, () -> {
                    serviceToTest.getCurrentCookOrders(restaurantUser);
                }
        );
    }

    @Test
    void testGetCurrentCookReturns() {
        Mockito.when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        Mockito.when(orderRepository.findCurrentCookOrders(testUser.getId())).thenReturn(List.of(testOrder));

        List<CookOrderView> actual = serviceToTest.getCurrentCookOrders(restaurantUser);
        Assertions.assertNotNull(actual.get(0));
        Assertions.assertEquals(actual.get(0).getDescription(), testOrder.getNotes());
    }

    @Test
    void testIsOwnerThrowsWhenOrderInvalid() {
        Assertions.assertThrows(
                RuntimeException.class, () -> {
                    serviceToTest.isOwner(5L, restaurantUser);
                }
        );

        Mockito.when(orderRepository.findById(5L)).thenReturn(Optional.of(testOrder));
        Assertions.assertThrows(
                RuntimeException.class, () -> {
                    serviceToTest.isOwner(5L, restaurantUser);
                }
        );
    }

    @Test
    void testIsOwnerReturns() {
        Mockito.when(orderRepository.findById(testOrder.getId())).thenReturn(Optional.of(testOrder));
        Mockito.when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        testUser.setBill(testBill);
        testOrder.setBill(testUser.getBill());

        boolean actual = serviceToTest.isOwner(testOrder.getId(), restaurantUser);
        Assertions.assertTrue(actual);
    }

    @Test
    void acceptCookOrder() {
        testOrder.setStatus(new OrderStatus().setName(OrderStatusEnum.PENDING));
        Mockito.when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        Mockito.when(orderRepository.findById(testOrder.getId())).thenReturn(Optional.of(testOrder));
        Mockito.when(orderStatusRepository.findByName(OrderStatusEnum.COOKING))
                .thenReturn(Optional.of(new OrderStatus().setName(OrderStatusEnum.COOKING)));

        serviceToTest.acceptCookOrder(restaurantUser, testOrder.getId());

        ArgumentCaptor<Order> argumentCaptor = ArgumentCaptor.forClass(Order.class);
        Mockito.verify(orderRepository).saveAndFlush(argumentCaptor.capture());

        Assertions.assertEquals(testUser.getId(), argumentCaptor.getValue().getCook().getId());

    }

    @Test
    void testReadyCookOrder() {
        testOrder.setStatus(new OrderStatus().setName(OrderStatusEnum.COOKING));
        Mockito.when(orderRepository.findById(testOrder.getId())).thenReturn(Optional.of(testOrder));
        Mockito.when(orderStatusRepository.findByName(OrderStatusEnum.READY))
                .thenReturn(Optional.of(new OrderStatus().setName(OrderStatusEnum.READY)));

        serviceToTest.readyCookOrder(testOrder.getId());

        ArgumentCaptor<Order> argumentCaptor = ArgumentCaptor.forClass(Order.class);
        Mockito.verify(orderRepository).saveAndFlush(argumentCaptor.capture());

        Assertions.assertEquals(OrderStatusEnum.READY, argumentCaptor.getValue().getStatus().getName());
    }

    @Test
    void testGetPendingOrders() {
        Mockito.when(orderRepository.getPendingOrders()).thenReturn(List.of(pendingOrder));

        List<CookOrderView> list = serviceToTest.getPendingOrders();
        Assertions.assertNotNull(list);
        CookOrderView cookOrderView = list.get(0);
        Assertions.assertEquals(pendingOrder.getId(), cookOrderView.getId());
        Assertions.assertEquals(pendingOrder.getItem().getName(), cookOrderView.getName());
        Assertions.assertEquals(pendingOrder.getNotes(), cookOrderView.getDescription());
        Assertions.assertEquals(pendingOrder.getItem().getImageUrl(), cookOrderView.getImageUrl());
    }

    @Test
    void testGetReadyOrders() {
        Mockito.when(orderRepository.findReadyOrders()).thenReturn(List.of(readyOrder));

        WaiterOrderView waiterOrderView = serviceToTest.getReadyOrders().get(0);

        Assertions.assertEquals(readyOrder.getId(), waiterOrderView.getId());
        Assertions.assertEquals(readyOrder.getItem().getName(), waiterOrderView.getName());
        Assertions.assertEquals(readyOrder.getNotes(), waiterOrderView.getDescription());
        Assertions.assertEquals(readyOrder.getItem().getImageUrl(), waiterOrderView.getImageUrl());
    }

    @Test
    void testGetCurrentWaiterOrders() {
        testOrder.setWaiter(testUser);
        Mockito.when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        Mockito.when(orderRepository.findCurrentWaiterOrders(testUser.getId())).thenReturn(List.of(testOrder));

        WaiterOrderView waiterOrderView = serviceToTest.getCurrentWaiterOrders(restaurantUser).get(0);
        Assertions.assertEquals(testOrder.getId(), waiterOrderView.getId());
        Assertions.assertEquals(testOrder.getBill().getTable().getNumber(), waiterOrderView.getTableView().getNumber());
        Assertions.assertEquals(testOrder.getNotes(), waiterOrderView.getDescription());
        Assertions.assertEquals(testOrder.getItem().getName(), waiterOrderView.getName());
        Assertions.assertEquals(testOrder.getItem().getImageUrl(), waiterOrderView.getImageUrl());
    }

}
