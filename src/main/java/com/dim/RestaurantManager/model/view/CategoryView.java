package com.dim.RestaurantManager.model.view;

import java.util.List;

public class CategoryView {
    private Long id;
    private String categoryName;
    private List<ItemView> items;

    public Long getId() {
        return id;
    }

    public CategoryView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public CategoryView setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public List<ItemView> getItems() {
        return items;
    }

    public CategoryView setItems(List<ItemView> items) {
        this.items = items;
        return this;
    }
}
