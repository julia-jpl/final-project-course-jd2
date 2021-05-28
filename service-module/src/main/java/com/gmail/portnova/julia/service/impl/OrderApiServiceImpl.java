package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.OrderRepository;
import com.gmail.portnova.julia.repository.model.Order;
import com.gmail.portnova.julia.service.OrderApiService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.OrderNotFoundException;
import com.gmail.portnova.julia.service.model.OrderApiDTO;
import com.gmail.portnova.julia.service.model.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.gmail.portnova.julia.service.constant.ExceptionMessageConstant.ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE;

@Service
@RequiredArgsConstructor
public class OrderApiServiceImpl implements OrderApiService {
    private final OrderRepository orderRepository;
    private final GeneralConverter<Order, OrderDTO> orderConverter;
    private final GeneralConverter<Order, OrderApiDTO> orderApiConverter;

    @Override
    @Transactional
    public List<OrderDTO> getOrders() {
        List<Order> orders = orderRepository.findAll();
        if (!orders.isEmpty()) {
            return orders.stream()
                    .map(orderConverter::convertObjectToDTO)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public OrderApiDTO getOrderApiByUuid(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        Order order = orderRepository.findByUuid(uuid);
        if (Objects.nonNull(order)) {
            return orderApiConverter.convertObjectToDTO(order);
        } else {
            throw new OrderNotFoundException(String.format(
                    ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE, Order.class, uuidString));
        }
    }


}
