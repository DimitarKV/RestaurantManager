package com.dim.RestaurantManager.repository;

import com.dim.RestaurantManager.model.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findAllByCategoryId(Long id);
}
