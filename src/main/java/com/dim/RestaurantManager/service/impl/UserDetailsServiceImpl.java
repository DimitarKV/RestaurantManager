package com.dim.RestaurantManager.service.impl;

import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.model.view.FoodTableView;
import com.dim.RestaurantManager.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return toUserDetails(this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found!")));
    }

    private UserDetails toUserDetails(User user) {
        FoodTableView tableView = null;
        if(user.getBill() != null)
            tableView = new FoodTableView()
                    .setNumber(user.getBill().getTable().getNumber())
                    .setDescription(user.getBill().getTable().getDescription());
        return new RestaurantUser(user.getUsername(), user.getPassword(), tableView, user.getRoles());
    }
}
