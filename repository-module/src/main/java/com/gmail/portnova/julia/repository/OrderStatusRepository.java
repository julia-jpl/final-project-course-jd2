package com.gmail.portnova.julia.repository;

import com.gmail.portnova.julia.repository.model.OrderStatus;
import com.gmail.portnova.julia.repository.model.OrderStatusNameEnum;

public interface OrderStatusRepository extends GenericRepository<Long, OrderStatus> {
    OrderStatus findByStatusName(OrderStatusNameEnum statusName);
}
