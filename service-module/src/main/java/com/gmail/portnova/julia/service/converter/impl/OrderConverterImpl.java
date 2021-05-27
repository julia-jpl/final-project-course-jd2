package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.Order;
import com.gmail.portnova.julia.repository.model.OrderDetail;
import com.gmail.portnova.julia.repository.model.OrderStatus;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.OrderDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.gmail.portnova.julia.service.constant.TimeFormatterConstant.DATE_TIME_FORMATTER;

@Component
public class OrderConverterImpl implements GeneralConverter<Order, OrderDTO> {
    @Override
    public OrderDTO convertObjectToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderUuid(order.getUuid().toString());
        orderDTO.setNumber(order.getNumber());
        orderDTO.setDate(order.getCreatedAt().format(DATE_TIME_FORMATTER));
        OrderStatus status = order.getStatus();
        if (Objects.nonNull(status)) {
            String statusName = status.getStatusName().name();
            orderDTO.setOrderStatus(statusName);
        }
        orderDTO.setSeller(order.getSeller());
        OrderDetail orderDetail = order.getOrderDetail();
        if (Objects.nonNull(orderDetail)) {
            orderDTO.setItemName(orderDetail.getItemName());
            orderDTO.setItemQuantity(orderDetail.getItemQuantity());
            orderDTO.setTotalPrice(orderDetail.getTotalPrice());
        }
        return orderDTO;
    }

    @Override
    public Order convertDTOToObject(OrderDTO object) {
        return null;
    }
}
