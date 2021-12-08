package com.dim.RestaurantManager.service.impl;

import com.dim.RestaurantManager.model.entity.Bill;
import com.dim.RestaurantManager.model.entity.FoodTable;
import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RestaurantUserTests {
    @Mock
    private UserRepository userRepository;

    private RestaurantUser serviceToTest;
    private User testUser;

    @BeforeEach
    void init() {
        testUser = new User()
                .setUsername("mitko")
                .setPassword("mitko")
                .setFirstName("first_name")
                .setLastName("last_name")
                .setAge(17)
                .setRoles(List.of());
        testUser.setId(1L);
        serviceToTest = new RestaurantUser(testUser.getUsername(), testUser.getPassword(), List.of(), userRepository);
    }

    @Test
    void testCanOrder() {
        Mockito.when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        testUser.setBill(new Bill().setTable(new FoodTable()));
        Assertions.assertTrue(serviceToTest.canOrder());
    }

    @Test
    void testOwesMoney() {
        Mockito.when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        testUser.setBill(new Bill().setTotalPrice(20.0));
        Assertions.assertTrue(serviceToTest.owesMoney());
    }

    @Test
    void testGetTableNumber() {
        Mockito.when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        testUser.setBill(new Bill().setTable(new FoodTable().setNumber(1)));
        Assertions.assertEquals(1, serviceToTest.getTableNumber());
    }

    @Test
    void testGetTableNumberReturnsNull() {
        Mockito.when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        Assertions.assertNull(serviceToTest.getTableNumber());
    }

    @Test
    void testGetTableTitle() {
        Mockito.when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        testUser.setBill(new Bill().setTable(new FoodTable().setTitle("title")));
        Assertions.assertEquals("title", serviceToTest.getTableTitle());
    }

    @Test
    void testGetTableTitleReturnsNull() {
        Mockito.when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        Assertions.assertNull(serviceToTest.getTableTitle());
    }
}
