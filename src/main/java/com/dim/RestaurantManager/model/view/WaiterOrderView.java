package com.dim.RestaurantManager.model.view;

public class WaiterOrderView {
    private Long id;
    private String imageUrl;
    private String name;
    private String description;
    private FoodTableView tableView;

    public Long getId() {
        return id;
    }

    public WaiterOrderView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public WaiterOrderView setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getName() {
        return name;
    }

    public WaiterOrderView setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WaiterOrderView setDescription(String description) {
        this.description = description;
        return this;
    }

    public FoodTableView getTableView() {
        return tableView;
    }

    public WaiterOrderView setTableView(FoodTableView tableView) {
        this.tableView = tableView;
        return this;
    }
}
