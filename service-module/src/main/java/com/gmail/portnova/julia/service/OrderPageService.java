package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.OrderDTO;
import com.gmail.portnova.julia.service.model.PageDTO;

import java.util.UUID;

public interface OrderPageService {
    PageDTO<OrderDTO> getCustomerUserOrderPage(int pageNumber, int maxResult, UUID uuid);
}
