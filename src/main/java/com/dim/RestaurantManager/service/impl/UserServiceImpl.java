package com.dim.RestaurantManager.service.impl;

import com.dim.RestaurantManager.model.entity.Order;
import com.dim.RestaurantManager.model.entity.Role;
import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.model.entity.enums.RoleEnum;
import com.dim.RestaurantManager.model.service.RegisterServiceModel;
import com.dim.RestaurantManager.model.view.ItemView;
import com.dim.RestaurantManager.model.view.OrderView;
import com.dim.RestaurantManager.repository.RoleRepository;
import com.dim.RestaurantManager.repository.UserRepository;
import com.dim.RestaurantManager.service.UserService;
import com.dim.RestaurantManager.service.exceptions.EntityNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean registerAndLoginUser(RegisterServiceModel serviceModel) {
        User user = new User()
                .setUsername(serviceModel.getUsername())
                .setPassword(this.passwordEncoder.encode(serviceModel.getPassword()));
        user = this.userRepository.saveAndFlush(user);
        if(user != null){
            UserDetails principal = this.userDetailsService.loadUserByUsername(user.getUsername());
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    principal,
                    user.getPassword(),
                    principal.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        }
        return false;
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void init() {
        initRoles();
        initUsers();
    }

    @Override
    public boolean usernameExists(String username) {
        return this.userRepository.findByUsername(username).isPresent();
    }

    @Override
    public Integer findTableNumberByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found!")).getBill().getTable().getNumber();
    }

    @Override
    public void updatePrincipal() {
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()),
                        SecurityContextHolder.getContext().getAuthentication().getCredentials(),
                        SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public List<OrderView> getOrders(RestaurantUser restaurantUser) {
        User user = userRepository
                .findByUsername(restaurantUser.getUsername())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "User with username " + restaurantUser.getUsername() + " not found!"));
        if(user.getBill() == null)
            return null;
        List<OrderView> retVal = mapToListOrderView(user.getBill().getOrders());
        return retVal;
    }

    private List<OrderView> mapToListOrderView(List<Order> orders) {
        return orders.stream().map(this::mapToOrderView).collect(Collectors.toList());
    }

    private OrderView mapToOrderView(Order order){
        return  new OrderView()
                .setItemView(new ItemView()
                        .setId(order.getItem().getId())
                        .setName(order.getItem().getName())
                        .setDescription(order.getItem().getDescription())
                        .setImageUrl(order.getItem().getImageUrl())
                        .setPrice(order.getItem().getPrice())
                )
                .setOrderStatus(order.getStatus().getName());
    }

    private void initRoles(){
        List<Role> roles =
                List.of(
                        new Role().setRole(RoleEnum.CUSTOMER),
                        new Role().setRole(RoleEnum.COOK),
                        new Role().setRole(RoleEnum.WAITER),
                        new Role().setRole(RoleEnum.HYGIENIST),
                        new Role().setRole(RoleEnum.MANAGER),
                        new Role().setRole(RoleEnum.BOSS)
                );
        roles = this.roleRepository.saveAllAndFlush(roles);
    }

    private void initUsers(){
        User user = new User()
                .setUsername("mitko")
                .setPassword(this.passwordEncoder.encode("mitko"))
                .setRoles(List.of(this.roleRepository.findByRole(RoleEnum.BOSS)));
        user = this.userRepository.saveAndFlush(user);

        User boss = new User()
                .setUsername("boss")
                .setPassword(this.passwordEncoder.encode("boss"))
                .setRoles(List.of(this.roleRepository.findByRole(RoleEnum.BOSS)));
        boss = this.userRepository.saveAndFlush(boss);

        User ginka = new User()
                .setUsername("ginka")
                .setPassword(this.passwordEncoder.encode("ginka"))
                .setRoles(List.of(this.roleRepository.findByRole(RoleEnum.HYGIENIST)));
        ginka = this.userRepository.saveAndFlush(ginka);
    }
}
