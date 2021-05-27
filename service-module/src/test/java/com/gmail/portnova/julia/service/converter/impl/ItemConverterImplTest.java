package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.Item;
import com.gmail.portnova.julia.repository.model.ItemDetail;
import com.gmail.portnova.julia.service.model.ItemDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemConverterImplTest {
    private final ItemConverterImpl itemConverter = new ItemConverterImpl();

    @Test
    void shouldConvertItemToItemDTOAndReturnRightId() {
        Item item = new Item();
        Long id = 1L;
        item.setId(id);

        ItemDTO itemDTO = itemConverter.convertObjectToDTO(item);
        assertEquals(id, itemDTO.getId());
    }

    @Test
    void shouldConvertItemToItemDTOAndReturnRightUuid() {
        Item item = new Item();
        UUID uuid = UUID.randomUUID();
        item.setUuid(uuid);

        ItemDTO itemDTO = itemConverter.convertObjectToDTO(item);
        assertEquals(uuid, itemDTO.getUuid());
    }

    @Test
    void shouldConvertItemToItemDTOAndReturnRightUniqueNumber() {
        Item item = new Item();
        String uniqueNumber = "number";
        item.setUniqueNumber(uniqueNumber);

        ItemDTO itemDTO = itemConverter.convertObjectToDTO(item);
        assertEquals(uniqueNumber, itemDTO.getUniqueNumber());
    }

    @Test
    void shouldConvertItemToItemDTOAndReturnRightName() {
        Item item = new Item();
        String name = "name";
        item.setName(name);

        ItemDTO itemDTO = itemConverter.convertObjectToDTO(item);
        assertEquals(name, itemDTO.getName());
    }

    @Test
    void shouldConvertItemToItemDTOAndReturnRightPriceWhenItemDetailNotNull() {
        Item item = new Item();
        ItemDetail itemDetail = new ItemDetail();
        BigDecimal price = new BigDecimal(11.2);
        itemDetail.setPrice(price);
        item.setItemDetail(itemDetail);

        ItemDTO itemDTO = itemConverter.convertObjectToDTO(item);
        assertEquals(price, itemDTO.getPrice());
    }
}