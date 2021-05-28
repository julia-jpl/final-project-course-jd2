package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.Order;
import com.gmail.portnova.julia.repository.model.OrderDetail;
import com.gmail.portnova.julia.repository.model.OrderStatus;
import com.gmail.portnova.julia.repository.model.OrderStatusNameEnum;
import com.gmail.portnova.julia.service.model.OrderDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderConverterImplTest {
    private final OrderConverterImpl orderConverter = new OrderConverterImpl();

    @Test
    void shouldConvertOrderToOrderDTOAndReturnRightId() {
        Order order = new Order();
        Long id = 1L;
        order.setId(id);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        order.setCreatedAt(date);

        OrderDTO orderDTO = orderConverter.convertObjectToDTO(order);
        assertEquals(id, orderDTO.getId());
    }

    @Test
    void shouldConvertOrderToOrderDTOAndReturnRightNumber() {
        Order order = new Order();
        String number = "11111";
        order.setNumber(number);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        order.setCreatedAt(date);

        OrderDTO orderDTO = orderConverter.convertObjectToDTO(order);
        assertEquals(number, orderDTO.getNumber());
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

        OrderDTO orderDTO = orderConverter.convertObjectToDTO(order);
        assertEquals(uuid.toString(), orderDTO.getOrderUuid());
    }

    @Test
    void shouldConvertOrderToOrderDTOAndReturnRightSeller() {
        Order order = new Order();
        String seller = "sellerName";
        order.setSeller(seller);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        order.setCreatedAt(date);

        OrderDTO orderDTO = orderConverter.convertObjectToDTO(order);
        assertEquals(seller, orderDTO.getSeller());
    }

    @Test
    void shouldConvertOrderToOrderDTOAndReturnRightStatus() {
        Order order = new Order();
        OrderStatus orderStatus = new OrderStatus();
        OrderStatusNameEnum orderStatusNameEnum = OrderStatusNameEnum.NEW;
        orderStatus.setStatusName(orderStatusNameEnum);
        order.setStatus(orderStatus);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        order.setCreatedAt(date);

        OrderDTO orderDTO = orderConverter.convertObjectToDTO(order);
        assertEquals(OrderStatusNameEnum.NEW.name(), orderDTO.getOrderStatus());
    }

    @Test
    void shouldConvertOrderToOrderDTOAndReturnRightItemName() {
        Order order = new Order();
        OrderDetail orderDetail = new OrderDetail();
        String itemName = "name";
        orderDetail.setItemName(itemName);
        order.setOrderDetail(orderDetail);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        order.setCreatedAt(date);

        OrderDTO orderDTO = orderConverter.convertObjectToDTO(order);
        assertEquals(itemName, orderDTO.getItemName());
    }

    @Test
    void shouldConvertOrderToOrderDTOAndReturnRightItemQuantity() {
        Order order = new Order();
        OrderDetail orderDetail = new OrderDetail();
        Integer itemQuantity = 2;
        orderDetail.setItemQuantity(itemQuantity);
        order.setOrderDetail(orderDetail);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        order.setCreatedAt(date);

        OrderDTO orderDTO = orderConverter.convertObjectToDTO(order);
        assertEquals(itemQuantity, orderDTO.getItemQuantity());
    }

    @Test
    void shouldConvertOrderToOrderDTOAndReturnRightTotalPrice() {
        Order order = new Order();
        OrderDetail orderDetail = new OrderDetail();
        BigDecimal totalPrice = new BigDecimal("2.00");
        orderDetail.setTotalPrice(totalPrice);
        order.setOrderDetail(orderDetail);
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        order.setCreatedAt(date);

        OrderDTO orderDTO = orderConverter.convertObjectToDTO(order);
        assertEquals(totalPrice, orderDTO.getTotalPrice());
    }
}