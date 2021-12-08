package com.dim.RestaurantManager.service.impl;

import com.dim.RestaurantManager.model.entity.Bill;
import com.dim.RestaurantManager.model.entity.FoodTable;
import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.model.view.FoodTableView;
import com.dim.RestaurantManager.repository.BillRepository;
import com.dim.RestaurantManager.repository.TableRepository;
import com.dim.RestaurantManager.repository.UserRepository;
import com.dim.RestaurantManager.utils.components.impl.ClassMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TableServiceImplTests {
    private TableServiceImpl serviceToTest;

    @Mock
    private TableRepository mockTableRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private BillRepository mockBillRepository;

    private User testUser;

    @BeforeEach
    void init() {
        serviceToTest = new TableServiceImpl(mockTableRepository, mockUserRepository, mockBillRepository, new ClassMapperImpl());

        testUser = new User()
                .setUsername("mitko")
                .setPassword("mitko")
                .setFirstName("first_name")
                .setLastName("last_name")
                .setAge(17)
                .setRoles(List.of());
    }

    @Test
    void testGetFreeTables() {
        FoodTable table1 = new FoodTable()
                .setTitle("table1")
                .setNumber(1)
                .setImageUrl("image1")
                .setDescription("description1");

        FoodTable table2 = new FoodTable()
                .setTitle("table2")
                .setNumber(2)
                .setImageUrl("image2")
                .setDescription("description2");
        table2.setBill(new Bill());

        Mockito.when(mockTableRepository.findAll()).thenReturn(List.of(
                table1,
                table2
        ));

        List<FoodTableView> tables = serviceToTest.getFreeTable();

        Assertions.assertNotNull(tables);
        Assertions.assertEquals(1, tables.size());
        Assertions.assertEquals(table1.getNumber(), tables.get(0).getNumber());
        Assertions.assertEquals(table1.getTitle(), tables.get(0).getTitle());
        Assertions.assertEquals(table1.getImageUrl(), tables.get(0).getImageUrl());
        Assertions.assertEquals(table1.getDescription(), tables.get(0).getDescription());
    }
}
