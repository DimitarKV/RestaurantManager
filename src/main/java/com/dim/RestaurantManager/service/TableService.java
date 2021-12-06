package com.dim.RestaurantManager.service;

import com.dim.RestaurantManager.model.view.FoodTableView;
import com.dim.RestaurantManager.service.impl.RestaurantUser;

import java.util.List;

public interface TableService {
    List<FoodTableView> getFreeTable();

    void init();

    List<FoodTableView> getOccupiedTables();

    void occupy(Integer number, RestaurantUser user);

    void join(Integer number, RestaurantUser user);
}
