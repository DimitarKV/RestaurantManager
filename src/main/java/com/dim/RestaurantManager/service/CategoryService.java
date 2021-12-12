package com.dim.RestaurantManager.service;


public interface CategoryService {
    void init();

    boolean hasCategory(String name);

    void addCategory(String name);

    boolean hasCategory(Long value);
}
