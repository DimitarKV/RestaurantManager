package com.dim.RestaurantManager.utils;

import com.dim.RestaurantManager.model.entity.enums.OrderStatusEnum;

import java.util.Map;

public class OrdersStatusConverter {
    private static final Map<OrderStatusEnum, String> statuses = Map.of(
            OrderStatusEnum.PENDING, "Изчакване",
            OrderStatusEnum.COOKING, "Готви се",
            OrderStatusEnum.READY, "Готова",
            OrderStatusEnum.FINISHED, "Завършена");
    private static final Map<OrderStatusEnum, String> classes = Map.of(
            OrderStatusEnum.PENDING, "btn-secondary",
            OrderStatusEnum.COOKING, "btn-warning",
            OrderStatusEnum.READY, "btn-primary",
            OrderStatusEnum.FINISHED, "btn-success");

    public static String toString(OrderStatusEnum statusEnum){
        return statuses.get(statusEnum);
    }

    public static String toBootstrapClass(OrderStatusEnum statusEnum) {
        return classes.get(statusEnum);
    }
}
