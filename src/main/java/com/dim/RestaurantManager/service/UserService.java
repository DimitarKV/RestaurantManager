package com.dim.RestaurantManager.service;

import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.model.service.RegisterServiceModel;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {
    boolean registerAndLoginUser(RegisterServiceModel serviceModel);

    Optional<User> getByUsername(String username);

    void init();

    boolean usernameExists(String username);
}
