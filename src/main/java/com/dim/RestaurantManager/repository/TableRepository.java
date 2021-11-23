package com.dim.RestaurantManager.repository;

import com.dim.RestaurantManager.model.entity.FoodTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<FoodTable, Long> {

    Optional<FoodTable> findByNumber(Integer number);
}
