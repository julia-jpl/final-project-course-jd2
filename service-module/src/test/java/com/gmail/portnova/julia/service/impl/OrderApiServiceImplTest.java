package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.OrderRepository;
import com.gmail.portnova.julia.repository.model.Order;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.OrderApiDTO;
import com.gmail.portnova.julia.service.model.OrderDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderApiServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private GeneralConverter<Order, OrderDTO> orderConverter;
    @InjectMocks
    private OrderApiServiceImpl orderApiService;

    @Test
    void shouldGetOrdersAndReturnEmptyList() {
        List<Order> orders = Collections.emptyList();
        when(orderRepository.findAll()).thenReturn(orders);

        List<OrderDTO> orderDTOS = orderApiService.getOrders();
        assertEquals(Collections.emptyList(), orderDTOS);
    }

    @Test
    void shouldGetOrdersAndReturnOrdersDTO() {
        Order order = new Order();
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        order.setCreatedAt(date);

        List<Order> orders = Collections.singletonList(order);
        when(orderRepository.findAll()).thenReturn(orders);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderUuid(uuid.toString());
        when(orderConverter.convertObjectToDTO(order)).thenReturn(orderDTO);

        List<OrderDTO> orderDTOS = orderApiService.getOrders();
        assertEquals(uuid.toString(), orderDTOS.get(0).getOrderUuid());
    }
}