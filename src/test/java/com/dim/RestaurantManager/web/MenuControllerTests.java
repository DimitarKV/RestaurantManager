package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.model.entity.Role;
import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.model.entity.enums.RoleEnum;
import com.dim.RestaurantManager.repository.UserRepository;
import com.dim.RestaurantManager.service.impl.RestaurantUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MenuControllerTests {
    @Autowired
    private MockMvc mockMvc;
    private RestaurantUser restaurantUser;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        restaurantUser =
                new RestaurantUser("customer", "customer",
                        List.of(new Role().setRole(RoleEnum.CUSTOMER)),
                        userRepository);
    }

    @Test
    void testFoodOrder() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/menu/order")
                        .param("itemId", "1")
                        .param("notes", "extra cheezzzz")
                        .with(user(restaurantUser))
                        .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/menu"));
    }
}
