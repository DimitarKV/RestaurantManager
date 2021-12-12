package com.dim.RestaurantManager.service;


import com.dim.RestaurantManager.model.entity.Category;

import java.util.List;

public interface CategoryService {
    void init();

    boolean hasCategory(String name);

    void addCategory(String name);

    boolean hasCategory(Long value);

    List<Category> findAll();
}
