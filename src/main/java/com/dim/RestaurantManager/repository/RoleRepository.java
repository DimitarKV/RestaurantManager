package com.dim.RestaurantManager.repository;

import com.dim.RestaurantManager.model.entity.Role;
import com.dim.RestaurantManager.model.entity.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(RoleEnum name);
}
