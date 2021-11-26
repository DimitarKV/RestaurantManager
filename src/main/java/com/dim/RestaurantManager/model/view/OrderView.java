package com.dim.RestaurantManager.model.view;


import com.dim.RestaurantManager.model.entity.enums.OrderStatusEnum;

public class OrderView {
    private ItemView itemView;
    private OrderStatusEnum orderStatus;

    public ItemView getItemView() {
        return itemView;
    }

    public OrderView setItemView(ItemView itemView) {
        this.itemView = itemView;
        return this;
    }

    public OrderStatusEnum getOrderStatus() {
        return orderStatus;
    }

    public OrderView setOrderStatus(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }
}
