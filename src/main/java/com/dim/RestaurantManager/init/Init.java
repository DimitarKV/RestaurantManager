package com.dim.RestaurantManager.init;

import com.dim.RestaurantManager.model.entity.Role;
import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.model.entity.enums.RoleEnum;
import com.dim.RestaurantManager.repository.RoleRepository;
import com.dim.RestaurantManager.repository.UserRepository;
import com.dim.RestaurantManager.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Init implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserService userService;

    public Init(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userService.init();

    }
}
