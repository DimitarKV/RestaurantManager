package com.dim.RestaurantManager.web.rest.binding;

import java.util.ArrayList;
import java.util.List;

public class CheckedOrdersBindingModel {
    private List<Long> orders = new ArrayList<>();

    public List<Long> getOrders() {
        return orders;
    }

    public CheckedOrdersBindingModel setOrders(List<Long> orders) {
        this.orders = orders;
        return this;
    }
}
