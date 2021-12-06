package com.dim.RestaurantManager.service.impl;

import com.dim.RestaurantManager.repository.UserRepository;
import com.dim.RestaurantManager.service.UserService;
import com.dim.RestaurantManager.service.exceptions.common.CommonErrorMessages;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class RestaurantUser extends User {
    private final UserRepository userRepository;

    public RestaurantUser(String username, String password, Collection<? extends GrantedAuthority> authorities, UserRepository userRepository) {
        super(username, password, authorities);
        this.userRepository = userRepository;
    }

    public RestaurantUser(String username, String password, Collection<? extends GrantedAuthority> authorities, UserRepository userRepository, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userRepository = userRepository;
    }

    public boolean canOrder() {
        com.dim.RestaurantManager.model.entity.User user = userRepository.findByUsername(super.getUsername())
                .orElseThrow(() -> CommonErrorMessages.username(super.getUsername()));
        return user.getBill() != null && user.getBill().getTable() != null;
    }

    public boolean owesMoney() {
        com.dim.RestaurantManager.model.entity.User user = userRepository.findByUsername(super.getUsername())
                .orElseThrow(() -> CommonErrorMessages.username(super.getUsername()));
        return user.getBill() != null && user.getBill().getTotalPrice() > 0;
    }

    public Integer getTableNumber() {
        com.dim.RestaurantManager.model.entity.User user = userRepository.findByUsername(super.getUsername())
                .orElseThrow(() -> CommonErrorMessages.username(super.getUsername()));
        if(user.getBill() == null)
            return null;
        return user.getBill().getTable().getNumber();
    }

    public String getTableTitle() {
        com.dim.RestaurantManager.model.entity.User user = userRepository.findByUsername(super.getUsername())
                .orElseThrow(() -> CommonErrorMessages.username(super.getUsername()));
        if(user.getBill() == null)
            return null;
        return user.getBill().getTable().getTitle();
    }
}
