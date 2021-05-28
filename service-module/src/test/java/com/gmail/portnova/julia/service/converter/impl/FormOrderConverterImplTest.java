package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.ItemRepository;
import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.*;
import com.gmail.portnova.julia.service.model.FormOrderDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.will;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FormOrderConverterImplTest {
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private FormOrderConverterImpl formOrderConverter;

    @Test
    void shouldGetBigDecimalTotalPrice(){
        FormOrderDTO formOrder = new FormOrderDTO();
        Integer itemQuantity = 2;
        formOrder.setItemQuantity(itemQuantity);
        
        Item item = new Item();
        ItemDetail itemDetail = new ItemDetail();
        BigDecimal price = new BigDecimal("3.00");
        itemDetail.setPrice(price);
        item.setItemDetail(itemDetail);

        BigDecimal totalPrice = new BigDecimal("6.00");

        assertEquals(totalPrice, formOrderConverter.getBigDecimal(formOrder, item));
    }




}