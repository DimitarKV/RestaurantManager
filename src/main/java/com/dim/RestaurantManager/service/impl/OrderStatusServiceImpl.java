package com.dim.RestaurantManager.service.impl;

import com.dim.RestaurantManager.model.entity.OrderStatus;
import com.dim.RestaurantManager.model.entity.enums.OrderStatusEnum;
import com.dim.RestaurantManager.repository.OrderStatusRepository;
import com.dim.RestaurantManager.service.OrderStatusService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    public OrderStatusServiceImpl(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public void init() {
        if(orderStatusRepository.count() == 0){
            //PENDING, COOKING, READY, FINISHED
            this.orderStatusRepository.saveAllAndFlush(List.of(
                    new OrderStatus().setName(OrderStatusEnum.PENDING),
                    new OrderStatus().setName(OrderStatusEnum.COOKING),
                    new OrderStatus().setName(OrderStatusEnum.READY),
                    new OrderStatus().setName(OrderStatusEnum.FINISHED)
            ));
        }

    }
}
