package com.dim.RestaurantManager.service.impl;

import com.dim.RestaurantManager.model.binding.ModifyUserRolesBindingModel;
import com.dim.RestaurantManager.model.binding.UpdateProfileBindingModel;
import com.dim.RestaurantManager.model.entity.Role;
import com.dim.RestaurantManager.model.entity.User;
import com.dim.RestaurantManager.model.entity.enums.RoleEnum;
import com.dim.RestaurantManager.model.service.UpdateProfileServiceModel;
import com.dim.RestaurantManager.repository.RoleRepository;
import com.dim.RestaurantManager.repository.UserRepository;
import com.dim.RestaurantManager.service.exceptions.EntityNotFoundException;
import com.dim.RestaurantManager.utils.components.impl.ClassMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {
    private UserServiceImpl serviceToTest;

    private User testUser, testUserInvalid, adminUser, cookUser, waiterUser;

    private final Role bossRole = new Role()
            .setRole(RoleEnum.BOSS);
    private final Role cookRole = new Role()
            .setRole(RoleEnum.COOK);
    private final Role waiterRole = new Role()
            .setRole(RoleEnum.WAITER);

    private RestaurantUser restaurantUser;
    private final PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private UserDetailsServiceImpl mockUserDetailsServiceImpl;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private SecurityContext mockSecurityContext;
    @Mock
    private Authentication mockAuthentication;


    @BeforeEach
    void init() {
        serviceToTest =
                new UserServiceImpl(mockUserRepository,
                        passwordEncoder,
                        mockUserDetailsServiceImpl,
                        mockRoleRepository,
                        new ClassMapperImpl());

        testUser = new User()
                .setUsername("mitko")
                .setFirstName("first_name")
                .setLastName("last_name")
                .setPassword("mitko")
                .setAge(17)
                .setRoles(List.of());
        testUser.setId(1L);

        bossRole.setId(1L);
        adminUser = new User()
                .setUsername("admin")
                .setFirstName("first_name")
                .setLastName("last_name")
                .setPassword("admin")
                .setAge(17)
                .setRoles(List.of(bossRole));

        cookRole.setId(2L);
        cookUser = new User()
                .setUsername("cook")
                .setFirstName("first_name")
                .setLastName("last_name")
                .setPassword("cook")
                .setAge(17)
                .setRoles(List.of(cookRole));

        waiterRole.setId(2L);
        waiterUser = new User()
                .setUsername("waiter")
                .setFirstName("first_name")
                .setLastName("last_name")
                .setPassword("waiter")
                .setAge(17)
                .setRoles(List.of(waiterRole));

        testUserInvalid = new User()
                .setUsername("pesho");


        restaurantUser =
                new RestaurantUser(testUser.getUsername(), testUser.getPassword(), testUser.getRoles(), mockUserRepository);



    }

    @Test
    void testGetByUsernameWithInvalidUsername() {
        Assertions.assertEquals(Optional.empty(), serviceToTest.getByUsername("invalid_username"));
    }

    @Test
    void testGetByUsernameWithValidUsername() {
        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        var actual = serviceToTest.getByUsername(testUser.getUsername());
        var expected = Optional.of(testUser);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testUsernameExistsReturnsTrueIfPresent() {
        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        var actual = serviceToTest.usernameExists(testUser.getUsername());

        Assertions.assertTrue(actual);
    }

    @Test
    void testUsernameExistsReturnsTrueIfNotPresent() {
        var actual = serviceToTest.usernameExists(testUserInvalid.getUsername());

        Assertions.assertFalse(actual);
    }

    @Test
    void testIsAdminTrue() {
        Mockito.when(mockUserRepository.findByUsername(adminUser.getUsername())).thenReturn(Optional.of(adminUser));

        var actual = serviceToTest.isAdmin(adminUser.getUsername());

        Assertions.assertTrue(actual);
    }

    @Test
    void testIsAdminThrows() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            serviceToTest.isAdmin(adminUser.getUsername());
        });
    }

    @Test
    void testIsAdminFalse() {
        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        var actual = serviceToTest.isAdmin(testUser.getUsername());

        Assertions.assertFalse(actual);
    }

    @Test
    void testIsCookTrue() {
        Mockito.when(mockUserRepository.findByUsername(cookUser.getUsername())).thenReturn(Optional.of(cookUser));

        var actual = serviceToTest.isCook(cookUser.getUsername());

        Assertions.assertTrue(actual);
    }

    @Test
    void testIsCookFalse() {
        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        var actual = serviceToTest.isCook(testUser.getUsername());

        Assertions.assertFalse(actual);
    }

    @Test
    void testIsWaiterTrue() {
        Mockito.when(mockUserRepository.findByUsername(waiterUser.getUsername())).thenReturn(Optional.of(waiterUser));

        var actual = serviceToTest.isWaiter(waiterUser.getUsername());

        Assertions.assertTrue(actual);
    }

    @Test
    void testIsWaiterFalse() {
        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        var actual = serviceToTest.isWaiter(testUser.getUsername());

        Assertions.assertFalse(actual);
    }

    @Test
    void getUserProfile() {
        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        UpdateProfileBindingModel actual = serviceToTest.getUserProfile(restaurantUser);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(testUser.getUsername(), actual.getUsername());
        Assertions.assertEquals(testUser.getFirstName(), actual.getFirstName());
        Assertions.assertEquals(testUser.getLastName(), actual.getLastName());
        Assertions.assertEquals(testUser.getAge(), actual.getAge());
        Assertions.assertNull(actual.getPassword());
        Assertions.assertNull(actual.getRepeatPassword());
    }

    @Test
    void testUpdateUserProfile() {


        UpdateProfileServiceModel serviceModel =
                new UpdateProfileServiceModel()
                        .setUsername("mmmm")
                        .setFirstName("nnnn")
                        .setLastName("oooo")
                        .setAge(18)
                        .setPassword("pppp");
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        Mockito.when(mockUserDetailsServiceImpl.loadUserByUsername(testUser.getUsername()))
                .thenReturn(new RestaurantUser(testUser.getUsername(), testUser.getPassword(),
                        testUser.getRoles(), mockUserRepository));
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(testUser.getUsername());
        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        serviceToTest.updateUserProfile(testUser.getUsername(), serviceModel);

        ArgumentCaptor<User> anyUser = ArgumentCaptor.forClass(User.class);
        Mockito.verify(mockUserRepository).saveAndFlush(anyUser.capture());

        Assertions.assertEquals(serviceModel.getUsername(), anyUser.getValue().getUsername());
        Assertions.assertEquals(serviceModel.getFirstName(), anyUser.getValue().getFirstName());
        Assertions.assertEquals(serviceModel.getLastName(), anyUser.getValue().getLastName());
        Assertions.assertEquals(serviceModel.getAge(), anyUser.getValue().getAge());
        Assertions.assertTrue(passwordEncoder.matches(serviceModel.getPassword(), anyUser.getValue().getPassword()));
    }

    @Test
    void testHasNotOccupied() {
        testUser.setBill(null);
        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        boolean actual = serviceToTest.hasNotOccupied(testUser.getUsername());
        Assertions.assertTrue(actual);
    }

    @Test
    void testModifyUserRoles() {
        ModifyUserRolesBindingModel bindingModel = new ModifyUserRolesBindingModel()
                .setId(testUser.getId())
                .setRoles(List.of(RoleEnum.BOSS, RoleEnum.COOK));

        Mockito.when(mockUserRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        Mockito.when(mockRoleRepository.findByRole(RoleEnum.BOSS)).thenReturn(Optional.of(new Role().setRole(RoleEnum.BOSS)));
        Mockito.when(mockRoleRepository.findByRole(RoleEnum.COOK)).thenReturn(Optional.of(new Role().setRole(RoleEnum.COOK)));
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        Mockito.when(mockUserDetailsServiceImpl.loadUserByUsername(testUser.getUsername()))
                .thenReturn(new RestaurantUser(testUser.getUsername(), testUser.getPassword(),
                        testUser.getRoles(), mockUserRepository));
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(testUser.getUsername());
        Mockito.when(mockUserRepository.saveAndFlush(Mockito.any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        serviceToTest.modifyUserRoles(bindingModel);

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(mockUserRepository).saveAndFlush(argumentCaptor.capture());

        Assertions.assertEquals(2,  argumentCaptor.getValue().getRoles().size());
        Assertions.assertTrue(argumentCaptor.getValue()
                .getRoles()
                .stream()
                .map(Role::getRole)
                .collect(Collectors.toList())
                .contains(RoleEnum.BOSS));
        Assertions.assertTrue(argumentCaptor.getValue()
                .getRoles()
                .stream()
                .map(Role::getRole)
                .collect(Collectors.toList())
                .contains(RoleEnum.COOK));
    }
}
