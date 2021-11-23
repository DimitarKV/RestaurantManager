package com.dim.RestaurantManager.model.view;

import com.dim.RestaurantManager.model.entity.MenuItem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MenuView {
    private Map<String, List<ItemView>> categories = new HashMap<>();

    public Map<String, List<ItemView>> getCategories() {
        return categories;
    }

    public MenuView setCategories(Map<String, List<ItemView>> categories) {
        this.categories = categories;
        return this;
    }

    public MenuView putMenuItem(MenuItem mi) {
        if (!categories.containsKey(mi.getCategory().getName()))
            categories.put(mi.getCategory().getName(), new LinkedList<>());
        categories
                .get(mi.getCategory().getName())
                .add(
                        new ItemView()
                                .setId(mi.getId())
                                .setName(mi.getItem().getName())
                                .setPrice(mi.getItem().getPrice())
                                .setDescription(mi.getItem().getDescription())
                                .setImageUrl(mi.getItem().getImageUrl())
                );

        return this;
    }
}
