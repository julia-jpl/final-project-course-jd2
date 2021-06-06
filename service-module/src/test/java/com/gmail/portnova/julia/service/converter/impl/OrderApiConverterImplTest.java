package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.*;
import com.gmail.portnova.julia.service.model.OrderApiDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.gmail.portnova.julia.service.constant.TimeFormatterConstant.DATE_TIME_FORMATTER;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderApiConverterImplTest {
    private final OrderApiConverterImpl orderApiConverter = new OrderApiConverterImpl();

    @Test
    void shouldConvertOrderToOrderApiDTOAndReturnRightId() {
        Order order = new Order();
        Long id = 1L;
        order.setId(id);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        order.setCreatedAt(date);

        OrderApiDTO orderApiDTO = orderApiConverter.convertObjectToDTO(order);
        assertEquals(id, orderApiDTO.getId());
    }

    @Test
    void shouldConvertOrderToOrderApiDTOAndReturnRightNumber() {
        Order order = new Order();
        String number = "11111";
        order.setNumber(number);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        order.setCreatedAt(date);

        OrderApiDTO orderApiDTO = orderApiConverter.convertObjectToDTO(order);
        assertEquals(number, orderApiDTO.getNumber());
    }

    @Test
    void shouldConvertOrderToOrderApiDTOAndReturnRightUuid() {
        Order order = new Order();
        String number = "11111";
        order.setNumber(number);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        order.setCreatedAt(date);

        OrderApiDTO orderApiDTO = orderApiConverter.convertObjectToDTO(order);
        assertEquals(uuid.toString(), orderApiDTO.getOrderUuid());
    }

    @Test
    void shouldConvertOrderToOrderApiDTOAndReturnRightCreatedAtDate() {
        Order order = new Order();
        String number = "11111";
        order.setNumber(number);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        order.setCreatedAt(date);

        OrderApiDTO orderApiDTO = orderApiConverter.convertObjectToDTO(order);
        assertEquals(date.format(DATE_TIME_FORMATTER), orderApiDTO.getDate());
    }

    @Test
    void shouldConvertOrderToOrderApiDTOAndReturnRightSeller() {
        Order order = new Order();
        String seller = "sellerName";
        order.setSeller(seller);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        order.setCreatedAt(date);

        OrderApiDTO orderApiDTO = orderApiConverter.convertObjectToDTO(order);
        assertEquals(seller, orderApiDTO.getSeller());
    }

    @Test
    void shouldConvertOrderToOrderApiDTOAndReturnRightStatus() {
        Order order = new Order();
        OrderStatus orderStatus = new OrderStatus();
        OrderStatusNameEnum orderStatusNameEnum = OrderStatusNameEnum.NEW;
        orderStatus.setStatusName(orderStatusNameEnum);
        order.setStatus(orderStatus);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        order.setCreatedAt(date);

        OrderApiDTO orderApiDTO = orderApiConverter.convertObjectToDTO(order);
        assertEquals(OrderStatusNameEnum.NEW.name(), orderApiDTO.getOrderStatus());
    }

    @Test
    void shouldConvertOrderToOrderApiDTOAndReturnRightItemName() {
        Order order = new Order();
        OrderDetail orderDetail = new OrderDetail();
        String itemName = "name";
        orderDetail.setItemName(itemName);
        order.setOrderDetail(orderDetail);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        order.setCreatedAt(date);

        OrderApiDTO orderApiDTO = orderApiConverter.convertObjectToDTO(order);
        assertEquals(itemName, orderApiDTO.getItemName());
    }

    @Test
    void shouldConvertOrderToOrderApiDTOAndReturnRightItemQuantity() {
        Order order = new Order();
        OrderDetail orderDetail = new OrderDetail();
        Integer itemQuantity = 2;
        orderDetail.setItemQuantity(itemQuantity);
        order.setOrderDetail(orderDetail);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        order.setCreatedAt(date);

        OrderApiDTO orderApiDTO = orderApiConverter.convertObjectToDTO(order);
        assertEquals(itemQuantity, orderApiDTO.getItemQuantity());
    }

    @Test
    void shouldConvertOrderToOrderApiDTOAndReturnRightTotalPrice() {
        Order order = new Order();
        OrderDetail orderDetail = new OrderDetail();
        BigDecimal totalPrice = new BigDecimal("2.00");
        orderDetail.setTotalPrice(totalPrice);
        order.setOrderDetail(orderDetail);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        order.setCreatedAt(date);

        OrderApiDTO orderApiDTO = orderApiConverter.convertObjectToDTO(order);
        assertEquals(totalPrice, orderApiDTO.getTotalPrice());
    }

    @Test
    void shouldConvertOrderToOrderApiDTOAndReturnRightCustomerIdentifier() {
        Order order = new Order();
        OrderDetail orderDetail = new OrderDetail();
        User customer = new User();
        String name = "name";
        customer.setFirstName(name);
        orderDetail.setCustomer(customer);
        order.setOrderDetail(orderDetail);

        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        order.setCreatedAt(date);

        OrderApiDTO orderApiDTO = orderApiConverter.convertObjectToDTO(order);
        assertEquals(name, orderApiDTO.getCustomerIdentifier());
    }

    @Test
    void shouldConvertOrderToOrderApiDTOAndReturnRightCustomerTelephone() {
        Order order = new Order();
        OrderDetail orderDetail = new OrderDetail();
        String telephone = "telephone";
        orderDetail.setCustomerTelephone(telephone);
        order.setOrderDetail(orderDetail);

        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        order.setCreatedAt(date);

        OrderApiDTO orderApiDTO = orderApiConverter.convertObjectToDTO(order);
        assertEquals(telephone, orderApiDTO.getCustomerTelephone());
    }
}