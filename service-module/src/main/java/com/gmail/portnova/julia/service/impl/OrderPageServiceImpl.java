package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.OrderRepository;
import com.gmail.portnova.julia.repository.model.Order;
import com.gmail.portnova.julia.service.OrderPageService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.OrderDTO;
import com.gmail.portnova.julia.service.model.PageDTO;
import com.gmail.portnova.julia.service.model.PageableOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.gmail.portnova.julia.service.util.PageUtil.getNumberOfPages;
import static com.gmail.portnova.julia.service.util.PageUtil.getStartPosition;

@Service
@RequiredArgsConstructor
public class OrderPageServiceImpl implements OrderPageService {
    private final OrderRepository orderRepository;
    private final GeneralConverter<Order, OrderDTO> orderConverter;

    @Override
    public PageDTO<OrderDTO> getCustomerUserOrderPage(int pageNumber, int maxResult, UUID customerUuid) {
        Long numberOfRows = orderRepository.countOrdersByCustomerUuid(customerUuid);
        PageableOrder pageDTO = new PageableOrder();
        Long numberOfPages = getNumberOfPages(numberOfRows, maxResult);
        pageDTO.setTotalPages(numberOfPages);
        int startPosition = getStartPosition(pageNumber, maxResult);
        List<Order> orders = orderRepository.findOrdersWithLimitByCustomerUuid(startPosition, maxResult, customerUuid);
        List<OrderDTO> orderDTOS = getOrderDTOList(orders);
        pageDTO.getObjects().addAll(orderDTOS);
        return pageDTO;
    }

    @Override
    public PageDTO<OrderDTO> getSaleUserOrderPage(int pageNumber, int maxResult, UUID sellerUuid) {
        Long numberOfRows = orderRepository.countOrdersBySellerUuid(sellerUuid);
        PageableOrder pageDTO = new PageableOrder();
        Long numberOfPages = getNumberOfPages(numberOfRows, maxResult);
        pageDTO.setTotalPages(numberOfPages);
        int startPosition = getStartPosition(pageNumber, maxResult);
        List<Order> orders = orderRepository.findOrdersWithLimitBySellerUuid(startPosition, maxResult, sellerUuid);
        List<OrderDTO> orderDTOS = getOrderDTOList(orders);
        pageDTO.getObjects().addAll(orderDTOS);
        return pageDTO;
    }

    protected List<OrderDTO> getOrderDTOList(List<Order> orders) {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        if (!orders.isEmpty()) {
            List<OrderDTO> list = new ArrayList<>();
            for (Order order : orders) {
                OrderDTO orderDTO = orderConverter.convertObjectToDTO(order);
                list.add(orderDTO);
            }
            orderDTOS = list;
        }
        return orderDTOS;
    }
}
