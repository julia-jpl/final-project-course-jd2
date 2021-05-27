package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.OrderRepository;
import com.gmail.portnova.julia.repository.model.Item;
import com.gmail.portnova.julia.repository.model.Order;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.ItemDTO;
import com.gmail.portnova.julia.service.model.OrderDTO;
import com.gmail.portnova.julia.service.model.PageDTO;
import com.gmail.portnova.julia.service.model.PageableOrder;
import com.gmail.portnova.julia.service.util.PageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderPageServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private GeneralConverter<Order, OrderDTO> orderConverter;
    @InjectMocks
    private OrderPageServiceImpl orderPageService;

    @Test
    void shouldGetCustomerUserOrderPage() {
        int pageNumber = 1;
        int maxResult = 10;
        Long numberOfRows = 1L;
        UUID customerUuid = UUID.randomUUID();
        when(orderRepository.countOrdersByCustomerUuid(customerUuid)).thenReturn(numberOfRows);

        Long numberOfPages = PageUtil.getNumberOfPages(numberOfRows, maxResult);

        PageableOrder pageDTO = new PageableOrder();
        pageDTO.setTotalPages(numberOfPages);

        int startPosition = PageUtil.getStartPosition(pageNumber, maxResult);

        Order order = new Order();
        List<Order> orders = Collections.singletonList(order);
        when(orderRepository.findOrdersWithLimitByCustomerUuid(startPosition, maxResult, customerUuid)).thenReturn(orders);

        OrderDTO orderDTO = new OrderDTO();
        when(orderConverter.convertObjectToDTO(order)).thenReturn(orderDTO);
        List<OrderDTO> orderDTOS = orderPageService.getOrderDTOList(orders);

        pageDTO.getObjects().addAll(orderDTOS);

        PageDTO<OrderDTO> resultPage = orderPageService.getCustomerUserOrderPage(pageNumber, maxResult, customerUuid);

        assertEquals(numberOfPages, resultPage.getTotalPages());
        assertEquals(orderDTO, resultPage.getObjects().get(0));
    }
    @Test
    void shouldGetSaleUserOrderPage() {
        int pageNumber = 1;
        int maxResult = 10;
        Long numberOfRows = 1L;
        UUID sellerUuid = UUID.randomUUID();
        when(orderRepository.countOrdersBySellerUuid(sellerUuid)).thenReturn(numberOfRows);

        Long numberOfPages = PageUtil.getNumberOfPages(numberOfRows, maxResult);

        PageableOrder pageDTO = new PageableOrder();
        pageDTO.setTotalPages(numberOfPages);

        int startPosition = PageUtil.getStartPosition(pageNumber, maxResult);

        Order order = new Order();
        List<Order> orders = Collections.singletonList(order);
        when(orderRepository.findOrdersWithLimitBySellerUuid(startPosition, maxResult, sellerUuid)).thenReturn(orders);

        OrderDTO orderDTO = new OrderDTO();
        when(orderConverter.convertObjectToDTO(order)).thenReturn(orderDTO);
        List<OrderDTO> orderDTOS = orderPageService.getOrderDTOList(orders);

        pageDTO.getObjects().addAll(orderDTOS);

        PageDTO<OrderDTO> resultPage = orderPageService.getSaleUserOrderPage(pageNumber, maxResult, sellerUuid);

        assertEquals(numberOfPages, resultPage.getTotalPages());
        assertEquals(orderDTO, resultPage.getObjects().get(0));
    }
}