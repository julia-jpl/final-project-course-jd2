package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.OrderStatus;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.StatusDTO;
import org.springframework.stereotype.Component;


@Component
public class OrderStatusConverterImpl implements GeneralConverter<OrderStatus, StatusDTO> {

    @Override
    public StatusDTO convertObjectToDTO(OrderStatus orderStatus) {
        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setId(orderStatus.getId());
        statusDTO.setName(orderStatus.getStatusName().name());
        return statusDTO;
    }

    @Override
    public OrderStatus convertDTOToObject(StatusDTO object) {
        throw new UnsupportedOperationException("This method hasn't been implemented");
    }
}
