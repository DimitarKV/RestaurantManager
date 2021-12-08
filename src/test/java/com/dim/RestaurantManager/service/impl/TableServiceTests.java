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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TableServiceTests {
    private TableServiceImpl serviceToTest;

    @Mock
    private TableRepository mockTableRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private BillRepository mockBillRepository;

    private User testUser;
    private FoodTable table1, table2;
    private RestaurantUser restaurantUser;

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
        testUser.setId(1L);

        restaurantUser = new RestaurantUser(testUser.getUsername(), testUser.getPassword(), List.of(), mockUserRepository);

        table1 = new FoodTable()
                .setTitle("table1")
                .setNumber(1)
                .setImageUrl("image1")
                .setDescription("description1")
                .setBill(null);

        table2 = new FoodTable()
                .setTitle("table2")
                .setNumber(2)
                .setImageUrl("image2")
                .setDescription("description2")
                .setBill(new Bill());
    }

    @Test
    void testGetFreeTables() {
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

    @Test
    void testGetOccupiedTables() {
        Mockito.when(mockTableRepository.findAll()).thenReturn(List.of(
                table1,
                table2
        ));

        List<FoodTableView> tables = serviceToTest.getOccupiedTables();

        Assertions.assertNotNull(tables);
        Assertions.assertEquals(1, tables.size());
        Assertions.assertEquals(table2.getNumber(), tables.get(0).getNumber());
        Assertions.assertEquals(table2.getTitle(), tables.get(0).getTitle());
        Assertions.assertEquals(table2.getImageUrl(), tables.get(0).getImageUrl());
        Assertions.assertEquals(table2.getDescription(), tables.get(0).getDescription());
    }

    @Test
    void testOccupyOccupiedTable() {
        Mockito.when(mockTableRepository.findByNumber(2)).thenReturn(Optional.of(table2));
        Assertions.assertThrows(RuntimeException.class, () -> {
           serviceToTest.occupy(table2.getNumber(), restaurantUser);
        });
    }

    @Test
    void testOccupyFreeTable() {
        Mockito.when(mockTableRepository.findByNumber(1)).thenReturn(Optional.of(table1));
        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        Mockito.when(mockBillRepository.saveAndFlush(Mockito.any(Bill.class))).thenAnswer(m -> m.getArguments()[0]);
        Mockito.when(mockUserRepository.saveAndFlush(Mockito.any(User.class))).thenAnswer(m -> m.getArguments()[0]);

        serviceToTest.occupy(table1.getNumber(), restaurantUser);

        ArgumentCaptor<Bill> argumentCaptor = ArgumentCaptor.forClass(Bill.class);
        Mockito.verify(mockBillRepository).saveAndFlush(argumentCaptor.capture());

        Assertions.assertEquals(argumentCaptor.getValue().getTable(), table1);
    }

    @Test
    void testJoinTable() {
        Mockito.when(mockTableRepository.findByNumber(2)).thenReturn(Optional.of(table2));
        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        Mockito.when(mockBillRepository.saveAndFlush(Mockito.any(Bill.class))).thenAnswer(m -> m.getArguments()[0]);
        Mockito.when(mockUserRepository.saveAndFlush(Mockito.any(User.class))).thenAnswer(m -> m.getArguments()[0]);

        serviceToTest.join(table2.getNumber(), restaurantUser);

        ArgumentCaptor<Bill> argumentCaptor = ArgumentCaptor.forClass(Bill.class);
        Mockito.verify(mockBillRepository).saveAndFlush(argumentCaptor.capture());

        Assertions.assertTrue(
                argumentCaptor
                        .getValue()
                        .getUsers()
                        .stream()
                        .anyMatch(u -> u.getId().equals(testUser.getId())));
    }
}
