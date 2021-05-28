package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.OrderSaleDTO;

public interface OrderSaleService {
    OrderSaleDTO getOrderByUuid(String orderUuid);
}
