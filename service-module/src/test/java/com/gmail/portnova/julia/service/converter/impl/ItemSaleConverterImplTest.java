package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.Item;
import com.gmail.portnova.julia.repository.model.ItemDetail;
import com.gmail.portnova.julia.service.model.ItemDTO;
import com.gmail.portnova.julia.service.model.ItemSaleDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ItemSaleConverterImplTest {
    private  final  ItemSaleConverterImpl itemSaleConverter = new ItemSaleConverterImpl();

    @Test
    void shouldConvertItemToItemSaleDTOAndReturnRightUuid() {
        Item item = new Item();
        UUID uuid = UUID.randomUUID();
        item.setUuid(uuid);

        ItemSaleDTO itemDTO = itemSaleConverter.convertObjectToDTO(item);
        assertEquals(uuid, itemDTO.getUuid());
    }

    @Test
    void shouldConvertItemToItemSaleDTOAndReturnRightUniqueNumber() {
        Item item = new Item();
        String uniqueNumber = "number";
        item.setUniqueNumber(uniqueNumber);

        ItemSaleDTO itemDTO = itemSaleConverter.convertObjectToDTO(item);
        assertEquals(uniqueNumber, itemDTO.getUniqueNumber());
    }

    @Test
    void shouldConvertItemToItemSaleDTOAndReturnRightName() {
        Item item = new Item();
        String name = "name";
        item.setName(name);

        ItemSaleDTO itemDTO  = itemSaleConverter.convertObjectToDTO(item);
        assertEquals(name, itemDTO.getName());
    }

    @Test
    void shouldConvertItemToItemSaleDTOAndReturnRightPriceWhenItemDetailNotNull() {
        Item item = new Item();
        ItemDetail itemDetail = new ItemDetail();
        BigDecimal price = new BigDecimal(11.2);
        itemDetail.setPrice(price);
        item.setItemDetail(itemDetail);

        ItemSaleDTO itemDTO = itemSaleConverter.convertObjectToDTO(item);
        assertEquals(price, itemDTO.getPrice());
    }
    @Test
    void shouldConvertItemToItemSaleDTOAndReturnRightDescription() {
        Item item = new Item();
        String description = "description";
        item.setDescription(description);

        ItemSaleDTO itemDTO = itemSaleConverter.convertObjectToDTO(item);
        assertEquals(description, itemDTO.getDescription());
    }

}