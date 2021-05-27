package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.Order;
import com.gmail.portnova.julia.repository.model.OrderDetail;
import com.gmail.portnova.julia.repository.model.OrderStatus;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.OrderApiDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.gmail.portnova.julia.service.constant.TimeFormatterConstant.DATE_TIME_FORMATTER;

@Component
public class OrderApiConverterImpl implements GeneralConverter<Order, OrderApiDTO> {
    @Override
    public OrderApiDTO convertObjectToDTO(Order order) {
        OrderApiDTO orderDTO = new OrderApiDTO();
        orderDTO.setId(order.getId());
        orderDTO.setNumber(order.getNumber());
        orderDTO.setOrderUuid(order.getUuid().toString());
        orderDTO.setDate(order.getCreatedAt().format(DATE_TIME_FORMATTER));
        orderDTO.setSeller(order.getSeller());
        OrderStatus status = order.getStatus();
        if (Objects.nonNull(status)) {
            orderDTO.setOrderStatus(status.getStatusName().name());
        }
        OrderDetail orderDetail = order.getOrderDetail();
        if (Objects.nonNull(orderDetail)) {
            orderDTO.setItemName(orderDetail.getItemName());
            orderDTO.setItemQuantity(orderDetail.getItemQuantity());
            orderDTO.setTotalPrice(orderDetail.getTotalPrice());
            User customer = orderDetail.getCustomer();
            if (Objects.nonNull(customer)) {
                String customerIdentifier = customer.getFirstName();
                orderDTO.setCustomerIdentifier(customerIdentifier);
            } else {
                orderDTO.setCustomerIdentifier(orderDetail.getCustomerIdentifier());
            }
            orderDTO.setCustomerTelephone(orderDetail.getCustomerTelephone());
        }
        return orderDTO;
    }

    @Override
    public Order convertDTOToObject(OrderApiDTO object) {
        throw new UnsupportedOperationException("This method hasn't been implemented");
    }
}
