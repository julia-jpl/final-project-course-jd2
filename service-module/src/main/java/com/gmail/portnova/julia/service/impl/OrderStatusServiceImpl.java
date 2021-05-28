package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.OrderStatusRepository;
import com.gmail.portnova.julia.repository.model.OrderStatus;
import com.gmail.portnova.julia.service.OrderStatusService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.StatusDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;
    private final GeneralConverter<OrderStatus, StatusDTO> orderStatusConverter;

    @Transactional
    @Override
    public List<StatusDTO> findAll() {
        List<OrderStatus> statuses = orderStatusRepository.findAll();
        if (!statuses.isEmpty()) {
            return statuses.stream()
                    .map(orderStatusConverter::convertObjectToDTO)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}
