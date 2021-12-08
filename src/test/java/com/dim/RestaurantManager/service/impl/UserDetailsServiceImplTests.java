package com.dim.RestaurantManager.service.impl;

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
public class UserDetailsServiceImplTests {
    private UserDetailsServiceImpl serviceToTest;

    @Mock
    private UserRepository mockUserRepository;
    private User testUser;

    @BeforeEach
    void init() {
        serviceToTest = new UserDetailsServiceImpl(mockUserRepository);

        testUser = new User()
                .setUsername("mitko")
                .setPassword("mitko")
                .setFirstName("first_name")
                .setLastName("last_name")
                .setAge(17)
                .setRoles(List.of());
    }

    @Test
    void testLoadByUsernamePresent(){
        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        RestaurantUser actual = (RestaurantUser) serviceToTest.loadUserByUsername(testUser.getUsername());

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(testUser.getUsername(), actual.getUsername());
    }
}
