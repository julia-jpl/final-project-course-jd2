package com.gmail.portnova.julia.repository;

import com.gmail.portnova.julia.repository.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends GenericRepository<Long, Order>{
    Long countOrdersByCustomerUuid(UUID customerUuid);

    List<Order> findOrdersWithLimitByCustomerUuid(int startPosition, int maxResult, UUID customerUuid);
}
