package com.dim.RestaurantManager.repository;

import com.dim.RestaurantManager.model.entity.ArchivedBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArchivedBillRepository extends JpaRepository<ArchivedBill, Long> {

    @Query("SELECT ab FROM ArchivedBill ab WHERE " +
            "ab.user.id = :id")
    List<ArchivedBill> findAllByUserId(Long id);

}
