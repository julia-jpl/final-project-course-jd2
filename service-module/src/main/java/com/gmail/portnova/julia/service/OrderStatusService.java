package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.StatusDTO;

import java.util.List;

public interface OrderStatusService {
    public List<StatusDTO> findAll();
}
