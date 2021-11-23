package com.dim.RestaurantManager.model.entity;

import com.dim.RestaurantManager.model.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@javax.persistence.Table(name = "users")
public class User extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    private Integer age;
    @ManyToOne
    private Bill bill;
    @ManyToMany
    @JoinTable(
            name = "users_archived_bills",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "bill_id")
    )
    private List<ArchivedBill> archivedBills;


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

    public Bill getBill() {
        return bill;
    }

    public User setBill(Bill bill) {
        this.bill = bill;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public User setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public List<ArchivedBill> getArchivedBills() {
        return archivedBills;
    }

    public User setArchivedBills(List<ArchivedBill> archivedBills) {
        this.archivedBills = archivedBills;
        return this;
    }
}
