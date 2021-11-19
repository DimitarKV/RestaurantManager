package com.dim.RestaurantManager.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@javax.persistence.Table(name = "users")
public class User extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @ManyToMany(mappedBy = "users")
    private List<Bill> bill;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    private Integer age;


    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<Bill> getBill() {
        return bill;
    }

    public User setBill(List<Bill> bill) {
        this.bill = bill;
        return this;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public User setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }
}
