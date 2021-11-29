package com.dim.RestaurantManager.model.view;

public class CookOrderView {
    private Long id;
    private String imageUrl;
    private String name;
    private String description;

    public Long getId() {
        return id;
    }

    public CookOrderView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CookOrderView setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getName() {
        return name;
    }

    public CookOrderView setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CookOrderView setDescription(String description) {
        this.description = description;
        return this;
    }
}
