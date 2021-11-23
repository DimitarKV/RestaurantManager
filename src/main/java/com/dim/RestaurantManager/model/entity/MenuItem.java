package com.dim.RestaurantManager.model.entity;

import com.dim.RestaurantManager.model.entity.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "menu")
public class MenuItem extends BaseEntity {
    @OneToOne
    private Item item;
    @ManyToOne
    private Category category;

    public Item getItem() {
        return item;
    }

    public MenuItem setItem(Item item) {
        this.item = item;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public MenuItem setCategory(Category category) {
        this.category = category;
        return this;
    }
}
