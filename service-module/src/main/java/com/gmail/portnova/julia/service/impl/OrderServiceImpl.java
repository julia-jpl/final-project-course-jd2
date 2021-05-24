package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.OrderRepository;
import com.gmail.portnova.julia.repository.OrderStatusRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.repository.model.Order;
import com.gmail.portnova.julia.repository.model.OrderStatus;
import com.gmail.portnova.julia.repository.model.OrderStatusNameEnum;
import com.gmail.portnova.julia.service.OrderService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.gmail.portnova.julia.service.util.PageUtil.getNumberOfPages;
import static com.gmail.portnova.julia.service.util.PageUtil.getStartPosition;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final GeneralConverter<Order, FormOrderDTO> formOrderConverter;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public void addOrderToDatabase(FormOrderDTO formOrder) {
        if (Objects.nonNull(formOrder)) {
            Order order = formOrderConverter.convertDTOToObject(formOrder);
            Long number = System.currentTimeMillis();
            String orderNumber = number.toString();
            order.setNumber(orderNumber);
            UUID uuid = UUID.randomUUID();
            order.setUuid(uuid);
            LocalDateTime createdAt = LocalDateTime.now();
            order.setCreatedAt(createdAt);
            OrderStatus orderStatus = orderStatusRepository.findByStatusName(OrderStatusNameEnum.NEW);
            order.setStatus(orderStatus);
            orderRepository.persist(order);
        }

    }

}
