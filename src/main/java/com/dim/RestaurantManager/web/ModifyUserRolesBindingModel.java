package com.dim.RestaurantManager.web;

import com.dim.RestaurantManager.model.entity.enums.RoleEnum;

import java.util.ArrayList;
import java.util.List;

public class ModifyUserRolesBindingModel {
    private Long id;
    private List<RoleEnum> roles = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public ModifyUserRolesBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public List<RoleEnum> getRoles() {
        return roles;
    }

    public ModifyUserRolesBindingModel setRoles(List<RoleEnum> roles) {
        this.roles = roles;
        return this;
    }
}
