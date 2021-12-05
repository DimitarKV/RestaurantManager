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
    public Integer findTableNumberByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> CommonErrorMessages.username(username))
                .getBill().getTable().getNumber();
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
    public List<OrderView> getOrders(RestaurantUser restaurantUser) {
        User user = userRepository
                .findByUsername(restaurantUser.getUsername())
                .orElseThrow(() -> CommonErrorMessages.username(restaurantUser.getUsername()));
        if (user.getBill() == null)
            return null;
        return classMapper.toListOrderView(user.getBill().getOrders());
    }

    @Override
    public List<UserView> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(classMapper::toUserView)
                .collect(Collectors.toList());
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
    public void acceptCookOrder(RestaurantUser restaurantUser, Long orderId) {
        User user = userRepository
                .findByUsername(restaurantUser.getUsername())
                .orElseThrow(() -> CommonErrorMessages.username(restaurantUser.getUsername()));
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> CommonErrorMessages.order(orderId));
        if (order.getStatus().getName() != OrderStatusEnum.PENDING)
            return;
        order.setCook(user);
        order.setStatus(orderStatusRepository.findByName(OrderStatusEnum.COOKING).get());
        orderRepository.saveAndFlush(order);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void cancelCookOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> CommonErrorMessages.order(orderId));
        if (order.getStatus().getName() != OrderStatusEnum.COOKING)
            return;
        order.setStatus(orderStatusRepository.findByName(OrderStatusEnum.PENDING).get());
        orderRepository.saveAndFlush(order);
    }

    @Override
    public void acceptWaiterOrder(RestaurantUser restaurantUser, Long orderId) {
        User user = userRepository
                .findByUsername(restaurantUser.getUsername())
                .orElseThrow(() -> CommonErrorMessages.username(restaurantUser.getUsername()));
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> CommonErrorMessages.order(orderId));

        if (order.getStatus().getName() != OrderStatusEnum.READY)
            return;
        order.setWaiter(user);
        order.setStatus(orderStatusRepository.findByName(OrderStatusEnum.TRAVELING).get());
        orderRepository.saveAndFlush(order);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void finishWaiterOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> CommonErrorMessages.order(orderId));
        if (order.getStatus().getName() != OrderStatusEnum.TRAVELING)
            return;
        order
                .setStatus(orderStatusRepository.findByName(OrderStatusEnum.FINISHED).get());
        orderRepository.saveAndFlush(order);
    }

    @Override
    public void cancelWaiterOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> CommonErrorMessages.order(orderId));
        if (order.getStatus().getName() != OrderStatusEnum.TRAVELING)
            return;
        order.setStatus(orderStatusRepository.findByName(OrderStatusEnum.READY).get());
        orderRepository.saveAndFlush(order);
    }

    @Override
    public void cancelUserOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> CommonErrorMessages.order(orderId));
        if (order.getStatus().getName() != OrderStatusEnum.PENDING)
            return;
        orderRepository.delete(order);
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

    @Override
    public void readyCookOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> CommonErrorMessages.order(orderId));
        if (order.getStatus().getName() != OrderStatusEnum.COOKING)
            return;
        order
                .setStatus(orderStatusRepository.findByName(OrderStatusEnum.READY).get());
        orderRepository.saveAndFlush(order);
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
                .setPassword(passwordEncoder.encode("customer"))
                .setRoles(List.of(customerRole));

        User ginka = new User()
                .setUsername("ginka")
                .setPassword("c3e8fa05cef1dc0b73548acf3d473c7b52150bbe13ce80adb9d42bace30204ab9a37dafb7287e5a5")
                .setRoles(List.of(hygienistRole));

        User waiter = new User()
                .setUsername("waiter")
                .setPassword(passwordEncoder.encode("waiter"))
                .setRoles(List.of(waiterRole));

        User cook = new User()
                .setUsername("cook")
                .setPassword(passwordEncoder.encode("cook"))
                .setRoles(List.of(cookRole));

        User manager = new User()
                .setUsername("manager")
                .setPassword(passwordEncoder.encode("manager"))
                .setRoles(List.of(managerRole));

        User boss = new User()
                .setUsername("boss")
                .setPassword("a17668370eae32cf970297933fc0a6096d989e32e3e11726aa34479bc43ff63b3a28f7ea9da8ba80")
                .setRoles(List.of(bossRole));

        userRepository.saveAllAndFlush(List.of(customer, ginka, waiter, cook, manager, boss));
    }
}
