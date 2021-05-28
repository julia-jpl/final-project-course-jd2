package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.OrderStatus;
import com.gmail.portnova.julia.repository.model.OrderStatusNameEnum;
import com.gmail.portnova.julia.service.model.StatusDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderStatusConverterImplTest {
    private final OrderStatusConverterImpl orderStatusConverter = new OrderStatusConverterImpl();

    @Test
    void shouldConvertOrderStatusToStatusDTOAndReturnRightName() {
        OrderStatus orderStatus = new OrderStatus();
        OrderStatusNameEnum statusName = OrderStatusNameEnum.NEW;
        orderStatus.setStatusName(statusName);
        StatusDTO statusDTO = orderStatusConverter.convertObjectToDTO(orderStatus);
        assertEquals(statusName.name(), statusDTO.getName());
    }

    @Test
    void shouldConvertOrderStatusToStatusDTOAndReturnRightId() {
        OrderStatus orderStatus = new OrderStatus();
        Long id = 1L;
        orderStatus.setId(id);
        OrderStatusNameEnum statusName = OrderStatusNameEnum.NEW;
        orderStatus.setStatusName(statusName);
        StatusDTO statusDTO = orderStatusConverter.convertObjectToDTO(orderStatus);
        assertEquals(id, statusDTO.getId());
    }

}