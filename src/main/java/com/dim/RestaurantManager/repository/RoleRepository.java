package com.dim.RestaurantManager.repository;

import com.dim.RestaurantManager.model.entity.Role;
import com.dim.RestaurantManager.model.entity.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleEnum name);
}
