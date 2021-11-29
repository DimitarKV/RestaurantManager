package com.dim.RestaurantManager.model.binding;

import org.hibernate.validator.constraints.Length;

public class RegisterBindingModel {
    @Length(min = 4, max = 20)
    private String username;
    @Length(min = 4, max = 20)
    private String password;
    @Length(min = 4, max = 20)
    private String repeatPassword;

    public String getUsername() {
        return username;
    }

    public RegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public RegisterBindingModel setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
        return this;
    }
}
