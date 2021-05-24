package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.FormOrderDTO;
import com.gmail.portnova.julia.service.model.OrderDTO;
import com.gmail.portnova.julia.service.model.PageDTO;

import java.util.UUID;

public interface OrderService {
    void addOrderToDatabase(FormOrderDTO formOrder);
}
