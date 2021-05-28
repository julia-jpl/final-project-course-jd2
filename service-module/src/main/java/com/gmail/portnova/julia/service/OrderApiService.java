package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.OrderApiDTO;
import com.gmail.portnova.julia.service.model.OrderDTO;

import java.util.List;

public interface OrderApiService {
    List<OrderDTO> getOrders();

    OrderApiDTO getOrderApiByUuid(String uuidString);
}
