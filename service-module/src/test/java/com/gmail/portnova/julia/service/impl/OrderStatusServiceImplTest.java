package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.OrderStatusRepository;
import com.gmail.portnova.julia.repository.model.OrderStatus;
import com.gmail.portnova.julia.repository.model.OrderStatusNameEnum;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.StatusDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderStatusServiceImplTest {
    @Mock
    private OrderStatusRepository orderStatusRepository;
    @Mock
    private GeneralConverter<OrderStatus, StatusDTO> orderStatusConverter;
    @InjectMocks
    private OrderStatusServiceImpl orderStatusService;

    @Test
    void shouldFindAll() {
        OrderStatus orderStatus = new OrderStatus();
        OrderStatusNameEnum statusName = OrderStatusNameEnum.NEW;
        orderStatus.setStatusName(statusName);

        List<OrderStatus> orderStatuses = Collections.singletonList(orderStatus);
        when(orderStatusRepository.findAll()).thenReturn(orderStatuses);

        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setName(OrderStatusNameEnum.NEW.name());

        when(orderStatusConverter.convertObjectToDTO(orderStatus)).thenReturn(statusDTO);

        List<StatusDTO> statusDTOS = orderStatusService.findAll();
        assertEquals(statusName.name(), statusDTOS.get(0).getName());
    }
    @Test
    void shouldFindAllAndReturnEmptyList() {

        List<OrderStatus> orderStatuses = Collections.emptyList();
        when(orderStatusRepository.findAll()).thenReturn(orderStatuses);

        List<StatusDTO> statusDTOS = orderStatusService.findAll();
        assertEquals(Collections.emptyList(), statusDTOS);
    }
}