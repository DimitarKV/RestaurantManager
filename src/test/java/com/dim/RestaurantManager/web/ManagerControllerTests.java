package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.model.binding.ManagerAddItemBindingModel;
import com.dim.RestaurantManager.model.entity.Role;
import com.dim.RestaurantManager.model.entity.enums.RoleEnum;
import com.dim.RestaurantManager.repository.CategoryRepository;
import com.dim.RestaurantManager.repository.UserRepository;
import com.dim.RestaurantManager.service.impl.RestaurantUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ManagerControllerTests {
    @Autowired
    private MockMvc mockMvc;
    private RestaurantUser restaurantManager;
    @Mock
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void init() {
        restaurantManager =
                new RestaurantUser("manager", "manager",
                        List.of(new Role().setRole(RoleEnum.MANAGER)),
                        userRepository);
    }

    @Test
    void test1() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/manager/category/add")
                                .param("categoryName", "Ribka")
                                .with(user(restaurantManager))
                                .with(csrf())
                )
                .andExpect(redirectedUrl("/menu"))
                .andExpect(status().is3xxRedirection());
        Assertions.assertTrue(categoryRepository.findByName("Ribka").isPresent());
    }

    @Test
    void test2() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/manager/item/add")
                        .param("categoryId", "1")
                        .param("imageUrl", "https://12345")
                        .param("itemName", "test")
                        .param("itemPrice", "11.9")
                        .param("itemDescription", "1234567890")
                        .with(user(restaurantManager))
                        .with(csrf())
        )
                .andExpect(redirectedUrl("/menu"))
                .andExpect(status().is3xxRedirection());

    }
}
