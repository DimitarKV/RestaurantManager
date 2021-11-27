package com.dim.RestaurantManager.service.impl;

import com.dim.RestaurantManager.model.view.FoodTableView;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class RestaurantUser extends User {
    private final FoodTableView tableView;

    public RestaurantUser(String username, String password, FoodTableView tableView, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.tableView = tableView;
    }

    public RestaurantUser(String username, String password, FoodTableView tableView, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.tableView = tableView;
    }

    public FoodTableView getTableView() {
        return tableView;
    }
}
