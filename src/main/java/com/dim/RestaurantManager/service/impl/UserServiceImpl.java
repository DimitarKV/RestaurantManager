package com.dim.RestaurantManager.service.impl;

import com.dim.RestaurantManager.model.binding.UpdateProfileBindingModel;
import com.dim.RestaurantManager.model.entity.Order;
import com.dim.RestaurantManager.model.entity.Role;
import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.model.entity.enums.OrderStatusEnum;
import com.dim.RestaurantManager.model.entity.enums.RoleEnum;
import com.dim.RestaurantManager.model.service.RegisterServiceModel;
import com.dim.RestaurantManager.model.service.UpdateProfileServiceModel;
import com.dim.RestaurantManager.model.view.OrderView;
import com.dim.RestaurantManager.model.view.UserView;
import com.dim.RestaurantManager.repository.OrderRepository;
import com.dim.RestaurantManager.repository.OrderStatusRepository;
import com.dim.RestaurantManager.repository.RoleRepository;
import com.dim.RestaurantManager.repository.UserRepository;
import com.dim.RestaurantManager.service.UserService;
import com.dim.RestaurantManager.service.exceptions.common.CommonErrorMessages;
import com.dim.RestaurantManager.utils.components.ClassMapper;
import com.dim.RestaurantManager.model.binding.ModifyUserRolesBindingModel;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final ClassMapper classMapper;
    private final SessionRegistry sessionRegistry;
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           UserDetailsServiceImpl userDetailsService,
                           RoleRepository roleRepository,
                           ClassMapper classMapper, SessionRegistry sessionRegistry, OrderRepository orderRepository, OrderStatusRepository orderStatusRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
        this.classMapper = classMapper;
        this.sessionRegistry = sessionRegistry;
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public boolean registerAndLoginUser(RegisterServiceModel serviceModel) {
        User user = new User()
                .setUsername(serviceModel.getUsername())
                .setPassword(this.passwordEncoder.encode(serviceModel.getPassword()))
                .setRoles(List.of(classMapper.toRole(RoleEnum.CUSTOMER)));
        user = this.userRepository.saveAndFlush(user);
        //TODO: find a better way of verifying that the username is actually unique
        if (user != null) {
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
    public void updatePrincipal() {
        UserDetails user = userDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user,
                user.getPassword(),
                user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void modifyUserRoles(ModifyUserRolesBindingModel bindingModel) {
        User user = this.userRepository
                .findById(bindingModel.getId())
                .orElseThrow(() -> CommonErrorMessages.userId(bindingModel.getId()))
                .setRoles(bindingModel
                        .getRoles()
                        .stream()
                        .map(classMapper::toRole)
                        .collect(Collectors.toList()));
        user = userRepository.saveAndFlush(user);
        if (user.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
            updatePrincipal();
    }

    @Override
    public boolean isAdmin(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> CommonErrorMessages.username(username))
                .getRoles().stream().anyMatch(r -> r.getRole() == RoleEnum.BOSS);
    }

    @Override
    public boolean isCook(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> CommonErrorMessages.username(username))
                .getRoles().stream().anyMatch(r -> r.getRole() == RoleEnum.COOK);
    }

    @Override
    public boolean isWaiter(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> CommonErrorMessages.username(username))
                .getRoles().stream().anyMatch(r -> r.getRole() == RoleEnum.WAITER);
    }

    @Override
    public UpdateProfileBindingModel getUserProfile(RestaurantUser user) {
        return classMapper.toUpdateProfileBindingModel(userRepository
                .findByUsername(user.getUsername())
                .orElseThrow(() -> CommonErrorMessages.username(user.getUsername())));
    }

    @Override
    public void updateUserProfile(String username, UpdateProfileServiceModel updateProfileServiceModel) {
        if (username != null) {
            User user = userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> CommonErrorMessages.username(username));
            user.setUsername(updateProfileServiceModel.getUsername());
            user.setFirstName(updateProfileServiceModel.getFirstName());
            user.setLastName(updateProfileServiceModel.getLastName());
            user.setAge(updateProfileServiceModel.getAge());
            if (updateProfileServiceModel.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(updateProfileServiceModel.getPassword()));
            }
            userRepository.saveAndFlush(user);
            updatePrincipal();
        }
    }



    @Override
    public boolean hasNotOccupied(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> CommonErrorMessages.username(username));
        return user.getBill() == null;
    }

    @Override
    public List<UserView> getUsers(Integer pageSize, Integer offset) {
        return userRepository.findPage(pageSize, offset).stream().map(classMapper::toUserView).collect(Collectors.toList());
    }

    @Override
    public Integer getUsersPageCount(Integer pageSize) {
        return (int)Math.ceil((double) userRepository.count() / pageSize);
    }

    private void initRoles() {
        List<Role> roles =
                List.of(
                        new Role().setRole(RoleEnum.CUSTOMER),
                        new Role().setRole(RoleEnum.COOK),
                        new Role().setRole(RoleEnum.WAITER),
                        new Role().setRole(RoleEnum.HYGIENIST),
                        new Role().setRole(RoleEnum.MANAGER),
                        new Role().setRole(RoleEnum.BOSS)
                );
        this.roleRepository.saveAllAndFlush(roles);
    }

    // CUSTOMER, HYGIENIST, WAITER, COOK, MANAGER, BOSS
    private void initUsers() {
        Role customerRole = classMapper.toRole(RoleEnum.CUSTOMER);
        Role hygienistRole = classMapper.toRole(RoleEnum.HYGIENIST);
        Role waiterRole = classMapper.toRole(RoleEnum.WAITER);
        Role cookRole = classMapper.toRole(RoleEnum.COOK);
        Role managerRole = classMapper.toRole(RoleEnum.MANAGER);
        Role bossRole = classMapper.toRole(RoleEnum.BOSS);

        User customer = new User()
                .setUsername("customer")
                .setPassword("0cf433220d1903e32e80d3579ffdcdb21d639f80caa2a79e82ab5a4154871d4d60d1b533c5da76a0")
                .setRoles(List.of(customerRole));

        User ginka = new User()
                .setUsername("ginka")
                .setPassword("c3e8fa05cef1dc0b73548acf3d473c7b52150bbe13ce80adb9d42bace30204ab9a37dafb7287e5a5")
                .setRoles(List.of(hygienistRole));

        User waiter = new User()
                .setUsername("waiter")
                .setPassword("7fb88cc7d8cc4e4317ed43d6aa6b67ab4186468de634cefbabd75424a8ed859572e90c516fbcb534")
                .setRoles(List.of(waiterRole));

        User cook = new User()
                .setUsername("cook")
                .setPassword("e5960fa0cd401a8b7745e09f83461c4274cb90b09d6d8111ac500ff604d345f512ba18bb53c74ba2")
                .setRoles(List.of(cookRole));

        User manager = new User()
                .setUsername("manager")
                .setPassword("4c352add46da74eb06c9ab6e3f5a74a50f4a70bb44904499746b2714cf37df6621a681d894949e05")
                .setRoles(List.of(managerRole));

        User boss = new User()
                .setUsername("boss")
                .setPassword("a17668370eae32cf970297933fc0a6096d989e32e3e11726aa34479bc43ff63b3a28f7ea9da8ba80")
                .setRoles(List.of(bossRole));

        userRepository.saveAllAndFlush(List.of(customer, ginka, waiter, cook, manager, boss));
    }
}
