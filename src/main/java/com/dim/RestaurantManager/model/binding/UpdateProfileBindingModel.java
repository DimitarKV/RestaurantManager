package com.dim.RestaurantManager.model.binding;

import com.dim.RestaurantManager.model.validator.annotations.BlankOrLength;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;

public class UpdateProfileBindingModel {
    @Length(min = 4, max = 20)
    private String username;
    @BlankOrLength(min = 4, max = 20)
    private String password;
    @BlankOrLength(min = 4, max = 20)
    private String repeatPassword;
    @BlankOrLength(min = 4, max = 20)
    private String firstName;
    @BlankOrLength(min = 4, max = 20)
    private String lastName;
    @Min(0)
    private Integer age;

    public String getUsername() {
        return username;
    }

    public UpdateProfileBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UpdateProfileBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UpdateProfileBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public UpdateProfileBindingModel setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UpdateProfileBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public UpdateProfileBindingModel setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
        return this;
    }
}
