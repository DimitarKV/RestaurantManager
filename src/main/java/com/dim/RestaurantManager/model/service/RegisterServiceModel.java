package com.dim.RestaurantManager.model.service;

public class RegisterServiceModel {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public RegisterServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
