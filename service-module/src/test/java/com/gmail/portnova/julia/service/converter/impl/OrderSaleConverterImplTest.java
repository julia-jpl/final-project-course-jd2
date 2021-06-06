package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.*;
import com.gmail.portnova.julia.service.model.OrderSaleDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderSaleConverterImplTest {
    private final OrderSaleConverterImpl orderSaleConverter = new OrderSaleConverterImpl();

    @Test
    void shouldConvertOrderToOrderSaleDTOAndReturnRightNumber() {
        Order order = new Order();
        String number = "11111";
        order.setNumber(number);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);

        OrderSaleDTO orderSaleDTO = orderSaleConverter.convertObjectToDTO(order);
        assertEquals(number, orderSaleDTO.getNumber());
    }

    @Test
    void shouldConvertOrderToOrderSaleDTOAndReturnRightUuid() {
        Order order = new Order();
        String number = "11111";
        order.setNumber(number);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);

        OrderSaleDTO orderSaleDTO = orderSaleConverter.convertObjectToDTO(order);
        assertEquals(uuid.toString(), orderSaleDTO.getOrderUuid());
    }

    @Test
    void shouldConvertOrderToOrderSaleDTOAndReturnRightStatus() {
        Order order = new Order();
        OrderStatus orderStatus = new OrderStatus();
        OrderStatusNameEnum orderStatusNameEnum = OrderStatusNameEnum.NEW;
        orderStatus.setStatusName(orderStatusNameEnum);
        order.setStatus(orderStatus);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);

        OrderSaleDTO orderSaleDTO = orderSaleConverter.convertObjectToDTO(order);
        assertEquals(OrderStatusNameEnum.NEW.name(), orderSaleDTO.getOrderStatus());
    }

    @Test
    void shouldConvertOrderToOrderSaleDTOAndReturnRightItemName() {
        Order order = new Order();
        OrderDetail orderDetail = new OrderDetail();
        String itemName = "name";
        orderDetail.setItemName(itemName);
        order.setOrderDetail(orderDetail);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);


        OrderSaleDTO orderSaleDTO = orderSaleConverter.convertObjectToDTO(order);
        assertEquals(itemName, orderSaleDTO.getItemName());
    }

    @Test
    void shouldConvertOrderToOrderSaleDTOAndReturnRightItemQuantity() {
        Order order = new Order();
        OrderDetail orderDetail = new OrderDetail();
        Integer itemQuantity = 2;
        orderDetail.setItemQuantity(itemQuantity);
        order.setOrderDetail(orderDetail);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);


        OrderSaleDTO orderSaleDTO = orderSaleConverter.convertObjectToDTO(order);
        assertEquals(itemQuantity, orderSaleDTO.getItemQuantity());
    }

    @Test
    void shouldConvertOrderToOrderSaleDTOAndReturnRightTotalPrice() {
        Order order = new Order();
        OrderDetail orderDetail = new OrderDetail();
        BigDecimal totalPrice = new BigDecimal("2.00");
        orderDetail.setTotalPrice(totalPrice);
        order.setOrderDetail(orderDetail);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);


        OrderSaleDTO orderSaleDTO = orderSaleConverter.convertObjectToDTO(order);
        assertEquals(totalPrice, orderSaleDTO.getTotalPrice());
    }

    @Test
    void shouldConvertOrderToOrderSaleDTOAndReturnRightCustomerIdentifier() {
        Order order = new Order();
        OrderDetail orderDetail = new OrderDetail();
        User customer = new User();
        String name = "name";
        customer.setFirstName(name);
        orderDetail.setCustomer(customer);
        order.setOrderDetail(orderDetail);

        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);


        OrderSaleDTO orderSaleDTO = orderSaleConverter.convertObjectToDTO(order);
        assertEquals(name, orderSaleDTO.getCustomerIdentifier());
    }

    @Test
    void shouldConvertOrderToOrderSaleDTOAndReturnRightCustomerTelephone() {
        Order order = new Order();
        OrderDetail orderDetail = new OrderDetail();
        String telephone = "telephone";
        orderDetail.setCustomerTelephone(telephone);
        order.setOrderDetail(orderDetail);

        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);

        OrderSaleDTO orderSaleDTO = orderSaleConverter.convertObjectToDTO(order);
        assertEquals(telephone, orderSaleDTO.getCustomerTelephone());
    }

}