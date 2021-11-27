package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.model.entity.enums.RoleEnum;

import java.util.List;

public class PatchUserBindingModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private List<RoleEnum> roles;
    private Integer age;

    public Long getId() {
        return id;
    }

    public PatchUserBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public PatchUserBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public PatchUserBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public PatchUserBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public List<RoleEnum> getRoles() {
        return roles;
    }

    public PatchUserBindingModel setRoles(List<RoleEnum> roles) {
        this.roles = roles;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public PatchUserBindingModel setAge(Integer age) {
        this.age = age;
        return this;
    }
}
