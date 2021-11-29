package com.dim.RestaurantManager.model.service;

public class UpdateProfileServiceModel {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Integer age;

    public String getUsername() {
        return username;
    }

    public UpdateProfileServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UpdateProfileServiceModel setPassword(String password) {
        this.password = password;
        if(password != null && password.equals(""))
            this.password = null;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UpdateProfileServiceModel setFirstName(String firstName) {
        this.firstName = firstName;
        if(firstName != null && firstName.equals(""))
            this.firstName = null;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UpdateProfileServiceModel setLastName(String lastName) {
        this.lastName = lastName;
        if(lastName != null && lastName.equals(""))
            this.lastName = null;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public UpdateProfileServiceModel setAge(Integer age) {
        this.age = age;
        return this;
    }
}
