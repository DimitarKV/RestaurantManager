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
public class TableControllerTests {
    @Autowired
    private MockMvc mockMvc;

    private User testUser;
    private RestaurantUser restaurantUser;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        testUser = new User()
                .setUsername("customer")
                .setPassword("customer")
                .setRoles(List.of(new Role().setRole(RoleEnum.CUSTOMER)));
        restaurantUser = new RestaurantUser(testUser.getUsername(), testUser.getPassword(), testUser.getRoles(), userRepository);
    }

    @Test
    void testUsernameNotAvailable() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/user/register/check/customer")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").value("false"));
    }

    @Test
    void test1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/table/occupy"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = "customer")
    void test2() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/table/register")
        )
                .andExpect(status().isOk());
    }

    @Test
    void testLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/login")
                .param("username", "customer")
                .param("password", "customer")
                .with(csrf())
        )
                .andExpect(redirectedUrl("/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testRegister() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/register")
                        .param("username", "customer3")
                        .param("password", "customer")
                        .param("repeatPassword", "customer")
                        .with(csrf())
                )
                .andExpect(redirectedUrl("/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testRegisterAlreadyRegistered() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/register")
                        .param("username", "customer")
                        .param("password", "customer")
                        .param("repeatPassword", "customer")
                        .with(csrf())
                )
                .andExpect(redirectedUrl("/user/register"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testMultipleRequests() throws Exception {
        UserDetails userDetails = restaurantUser;
        mockMvc.perform(
                MockMvcRequestBuilders
                        .patch("/user/profile")
                        .with(user(userDetails))
                        .param("username", "customer")
                        .param("firstName", "first_name")
                        .param("password", "")
                        .param("repeatPassword", "")
                        .with(csrf())
        )
                .andExpect(redirectedUrl("/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testOrdersFetch() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/user/orders-rest")
                        .with(user(restaurantUser))
        )
                .andExpect(status().isOk());
    }

    @Test
    void testUsernameAvailable() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/user/register/check/availableUsernameThatNooneIsGonnaUse")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").value("true"));
    }
}
