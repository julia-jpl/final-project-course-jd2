package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.ItemRepository;
import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.Item;
import com.gmail.portnova.julia.repository.model.Order;
import com.gmail.portnova.julia.repository.model.OrderDetail;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.ItemNotFoundException;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.model.FormOrderDTO;
import liquibase.pro.packaged.V;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import static com.gmail.portnova.julia.service.constant.ExceptionMessageConstant.ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE;

@Component
@RequiredArgsConstructor
public class FormOrderConverterImpl implements GeneralConverter<Order, FormOrderDTO> {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    @Override
    public FormOrderDTO convertObjectToDTO(Order object) {
        return null;
    }

    @Override
    public Order convertDTOToObject(FormOrderDTO formOrder) {
        Order order = new Order();
        UUID sellerUuid = UUID.fromString(formOrder.getSellerUuid());
        User seller = userRepository.findByUuid(sellerUuid);
        if (Objects.nonNull(seller)) {
            order.setSaleUser(seller);
            String sellerName = String.join(" ", seller.getLastName(), seller.getFirstName());
            order.setSeller(sellerName);
        } else {
            throw new UserNotFoundException(String.format(
                    ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE, User.class, sellerUuid));
        }
        OrderDetail orderDetail = getOrderDetail(formOrder);
        order.setOrderDetail(orderDetail);
        orderDetail.setOrder(order);
        return order;
    }

    protected OrderDetail getOrderDetail(FormOrderDTO formOrder) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCustomerTelephone(formOrder.getCustomerTel());
        orderDetail.setItemQuantity(formOrder.getItemQuantity());
        UUID itemUuid = UUID.fromString(formOrder.getItemUuid());
        Item item = itemRepository.findByUuid(itemUuid);
        if (Objects.nonNull(item)) {
            orderDetail.setItemName(item.getName());
            BigDecimal totalPrice = getBigDecimal(formOrder, item);
            orderDetail.setTotalPrice(totalPrice);
        } else {
            throw new ItemNotFoundException(String.format(ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE, Item.class, itemUuid));
        }
        User customer = userRepository.findByUuid(formOrder.getCustomerUuid());
        if (Objects.nonNull(customer)) {
            orderDetail.setCustomer(customer);
            String customerIdentifier = String.join(" ", customer.getLastName(), customer.getFirstName());
            orderDetail.setCustomerIdentifier(customerIdentifier);

        } else {
            throw new UserNotFoundException(String.format(
                    ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE, User.class, formOrder.getCustomerUuid()));
        }
        return orderDetail;
    }

    protected BigDecimal getBigDecimal(FormOrderDTO formOrder, Item item) {
        BigDecimal price = item.getItemDetail().getPrice();
        BigDecimal totalPrice = price.multiply(new BigDecimal(formOrder.getItemQuantity()));
        return totalPrice;
    }
}
