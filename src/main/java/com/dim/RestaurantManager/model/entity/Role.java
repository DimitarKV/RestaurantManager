package com.dim.RestaurantManager.model.entity;

import com.dim.RestaurantManager.model.entity.enums.RoleEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    public RoleEnum getRole() {
        return role;
    }

    public Role setRole(RoleEnum role) {
        this.role = role;
        return this;
    }
}
