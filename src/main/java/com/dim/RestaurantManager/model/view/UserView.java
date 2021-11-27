package com.dim.RestaurantManager.model.view;

import com.dim.RestaurantManager.model.entity.enums.RoleEnum;

import java.util.List;

public class UserView {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private List<RoleEnum> roles;
    private Integer age;

    public String getFirstName() {
        return firstName;
    }

    public UserView setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserView setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserView setUsername(String username) {
        this.username = username;
        return this;
    }

    public List<RoleEnum> getRoles() {
        return roles;
    }

    public UserView setRoles(List<RoleEnum> roles) {
        this.roles = roles;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public UserView setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserView setId(Long id) {
        this.id = id;
        return this;
    }
}
