package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.OrderRepository;
import com.gmail.portnova.julia.repository.model.Order;
import com.gmail.portnova.julia.service.OrderSaleService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.OrderNotFoundException;
import com.gmail.portnova.julia.service.model.OrderSaleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;

import static com.gmail.portnova.julia.service.constant.ExceptionMessageConstant.ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE;

@Service
@RequiredArgsConstructor
public class OrderSaleServiceImpl implements OrderSaleService {
    private final OrderRepository orderRepository;
    private final GeneralConverter<Order, OrderSaleDTO> orderSaleConverter;

    @Override
    @Transactional
    public OrderSaleDTO getOrderByUuid(String orderUuid) {
        UUID uuid = UUID.fromString(orderUuid);
        Order order = orderRepository.findByUuid(uuid);
        if (Objects.nonNull(order)) {
            return orderSaleConverter.convertObjectToDTO(order);
        } else {
            throw new OrderNotFoundException(String.format(
                    ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE, Order.class, orderUuid));
        }
    }
}
