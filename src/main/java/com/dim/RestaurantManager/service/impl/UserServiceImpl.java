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
import com.dim.RestaurantManager.service.exceptions.EntityNotFoundException;
import com.dim.RestaurantManager.utils.components.ClassMapper;
import com.dim.RestaurantManager.model.binding.ModifyUserRolesBindingModel;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
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
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found!")).getBill().getTable().getNumber();
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
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "User with username " + restaurantUser.getUsername() + " not found!"));
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
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + bindingModel.getId() + " not found!"))
                .setRoles(bindingModel
                        .getRoles()
                        .stream()
                        .map(classMapper::toRole)
                        .collect(Collectors.toList()));
        user = userRepository.saveAndFlush(user);
        if(user.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
            updatePrincipal();
    }

    @Override
    public boolean isAdmin(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username: " + username + " not found"))
                .getRoles().stream().anyMatch(r -> r.getRole() == RoleEnum.BOSS);
    }

    @Override
    public UpdateProfileBindingModel getUserProfile(RestaurantUser user) {
        return classMapper.toUpdateProfileBindingModel(userRepository
                .findByUsername(user.getUsername())
                .orElseThrow(() ->
                        new EntityNotFoundException("User with username: " + user.getUsername() + " not found!")));
    }

    @Override
    public void updateUserProfile(String username, UpdateProfileServiceModel updateProfileServiceModel) {
        if(username != null){
            User user = userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException("User with username: " + username + " not found!"));
            user.setUsername(updateProfileServiceModel.getUsername());
            user.setFirstName(updateProfileServiceModel.getFirstName());
            user.setLastName(updateProfileServiceModel.getLastName());
            user.setAge(updateProfileServiceModel.getAge());
            if(updateProfileServiceModel.getPassword() != null){
                user.setPassword(passwordEncoder.encode(updateProfileServiceModel.getPassword()));
            }
            userRepository.saveAndFlush(user);
            updatePrincipal();
        }
    }

    @Override
    public void acceptOrder(RestaurantUser restaurantUser, Long orderId) {
        User user = userRepository
                .findByUsername(restaurantUser.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User with username: " + restaurantUser.getUsername() + " not found!"));
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order with id: " + orderId + " not found!"));
        if(order.getStatus().getName() != OrderStatusEnum.PENDING)
            return;
        order.setExecutor(user);
        order.setStatus(orderStatusRepository.findByName(OrderStatusEnum.COOKING).get());
        orderRepository.saveAndFlush(order);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void readyOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order with id: " + orderId + " not found!"));
        if(order.getStatus().getName() != OrderStatusEnum.COOKING)
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

    private void initUsers() {
        Role bossRole = classMapper.toRole(RoleEnum.BOSS);
        Role customerRole = classMapper.toRole(RoleEnum.CUSTOMER);
        Role cookRole = classMapper.toRole(RoleEnum.COOK);
        Role hygienistRole = classMapper.toRole(RoleEnum.HYGIENIST);

        User mitko = new User()
                .setUsername("mitko")
                .setPassword("d9e02a4d657082ade25a04d9cab6bd99acd9984d98ac70402dccddd373476aec182f714ba9856dd7")
                .setRoles(List.of(bossRole, customerRole));
        User mitko1 = new User()
                .setUsername("mita43to")
                .setPassword("d9e02a4d657082ade25a04d9cab6bd99acd9984d98ac70402dccddd373476aec182f714ba9856dd7")
                .setRoles(List.of(customerRole));
        User mitko2 = new User()
                .setUsername("chef")
                .setPassword("d9e02a4d657082ade25a04d9cab6bd99acd9984d98ac70402dccddd373476aec182f714ba9856dd7")
                .setRoles(List.of(bossRole, cookRole));
        User mitko3 = new User()
                .setUsername("mitko3")
                .setPassword("d9e02a4d657082ade25a04d9cab6bd99acd9984d98ac70402dccddd373476aec182f714ba9856dd7")
                .setRoles(List.of(bossRole, customerRole));
        User mitko4 = new User()
                .setUsername("mitko4")
                .setPassword("d9e02a4d657082ade25a04d9cab6bd99acd9984d98ac70402dccddd373476aec182f714ba9856dd7")
                .setRoles(List.of(bossRole, customerRole));
        User mitko5 = new User()
                .setUsername("mitko5")
                .setPassword("d9e02a4d657082ade25a04d9cab6bd99acd9984d98ac70402dccddd373476aec182f714ba9856dd7")
                .setRoles(List.of(bossRole, customerRole));
        User mitko6 = new User()
                .setUsername("mitko6")
                .setPassword("d9e02a4d657082ade25a04d9cab6bd99acd9984d98ac70402dccddd373476aec182f714ba9856dd7")
                .setRoles(List.of(bossRole, customerRole));
        User mitko7 = new User()
                .setUsername("mitko7")
                .setPassword("d9e02a4d657082ade25a04d9cab6bd99acd9984d98ac70402dccddd373476aec182f714ba9856dd7")
                .setRoles(List.of(bossRole, customerRole));
        User mitko8 = new User()
                .setUsername("mitko8")
                .setPassword("d9e02a4d657082ade25a04d9cab6bd99acd9984d98ac70402dccddd373476aec182f714ba9856dd7")
                .setRoles(List.of(bossRole, customerRole));
        User mitko9 = new User()
                .setUsername("mitko9")
                .setPassword("d9e02a4d657082ade25a04d9cab6bd99acd9984d98ac70402dccddd373476aec182f714ba9856dd7")
                .setRoles(List.of(bossRole, customerRole));
        User mitko10 = new User()
                .setUsername("mitko10")
                .setPassword("d9e02a4d657082ade25a04d9cab6bd99acd9984d98ac70402dccddd373476aec182f714ba9856dd7")
                .setRoles(List.of(bossRole, customerRole));
        User mitko11 = new User()
                .setUsername("mitko11")
                .setPassword("d9e02a4d657082ade25a04d9cab6bd99acd9984d98ac70402dccddd373476aec182f714ba9856dd7")
                .setRoles(List.of(bossRole, customerRole));
        User mitko12 = new User()
                .setUsername("mitko12")
                .setPassword("d9e02a4d657082ade25a04d9cab6bd99acd9984d98ac70402dccddd373476aec182f714ba9856dd7")
                .setRoles(List.of(bossRole, customerRole));
        User mitko13 = new User()
                .setUsername("mitko13")
                .setPassword("d9e02a4d657082ade25a04d9cab6bd99acd9984d98ac70402dccddd373476aec182f714ba9856dd7")
                .setRoles(List.of(bossRole, customerRole));
        User mitko14 = new User()
                .setUsername("mitko14")
                .setPassword("d9e02a4d657082ade25a04d9cab6bd99acd9984d98ac70402dccddd373476aec182f714ba9856dd7")
                .setRoles(List.of(bossRole, customerRole));
        User mitko15 = new User()
                .setUsername("mitko15")
                .setPassword("d9e02a4d657082ade25a04d9cab6bd99acd9984d98ac70402dccddd373476aec182f714ba9856dd7")
                .setRoles(List.of(bossRole, customerRole));
        User boss = new User()
                .setUsername("boss")
                .setPassword("a17668370eae32cf970297933fc0a6096d989e32e3e11726aa34479bc43ff63b3a28f7ea9da8ba80")
                .setRoles(List.of(bossRole, customerRole));
        User ginka = new User()
                .setUsername("ginka")
                .setPassword("c3e8fa05cef1dc0b73548acf3d473c7b52150bbe13ce80adb9d42bace30204ab9a37dafb7287e5a5")
                .setRoles(List.of(hygienistRole));

        userRepository.saveAllAndFlush(List.of(
                mitko, mitko1, mitko2, mitko3, mitko4, mitko5,
                mitko6, mitko7, mitko8, mitko9, mitko10, mitko11,
                mitko12, mitko13, mitko14, mitko15, boss, ginka
        ));
    }
}
