package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.OrderRepository;
import com.gmail.portnova.julia.repository.model.Order;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.OrderNotFoundException;
import com.gmail.portnova.julia.service.model.OrderSaleDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderSaleServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private GeneralConverter<Order, OrderSaleDTO> orderSaleConverter;
    @InjectMocks
    private OrderSaleServiceImpl orderSaleService;

    @Test
    void shouldGetOrderByUuid() {
        Order order = new Order();
        UUID orderUuid = UUID.randomUUID();
        order.setUuid(orderUuid);

        when(orderRepository.findByUuid(orderUuid)).thenReturn(order);

        OrderSaleDTO orderSaleDTO = new OrderSaleDTO();
        orderSaleDTO.setOrderUuid(orderUuid.toString());

        when(orderSaleConverter.convertObjectToDTO(order)).thenReturn(orderSaleDTO);

        OrderSaleDTO orderSaleResult = orderSaleService.getOrderByUuid(orderUuid.toString());
        assertEquals(orderUuid.toString(), orderSaleResult.getOrderUuid());
    }
    @Test
    void shouldNotGetOrderByUuidAndThrowException() {
        UUID orderUuid = UUID.randomUUID();

        when(orderRepository.findByUuid(orderUuid)).thenReturn(null);
        assertThrows(OrderNotFoundException.class, ()-> orderSaleService.getOrderByUuid(orderUuid.toString()));
    }
}