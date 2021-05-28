package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.Order;
import com.gmail.portnova.julia.repository.model.OrderDetail;
import com.gmail.portnova.julia.repository.model.OrderStatus;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.OrderSaleDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class OrderSaleConverterImpl implements GeneralConverter<Order, OrderSaleDTO> {

    @Override
    public OrderSaleDTO convertObjectToDTO(Order order) {
        OrderSaleDTO orderDTO = new OrderSaleDTO();
        orderDTO.setNumber(order.getNumber());
        orderDTO.setOrderUuid(order.getUuid().toString());
        OrderStatus status = order.getStatus();
        if (Objects.nonNull(status)) {
            orderDTO.setOrderStatus(status.getStatusName().name());
            orderDTO.setOrderStatusId(status.getId());
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
    public Order convertDTOToObject(OrderSaleDTO object) {
        throw new UnsupportedOperationException("This method hasn't been implemented");
    }
}
