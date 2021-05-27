package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.Item;
import com.gmail.portnova.julia.repository.model.ItemDetail;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.ItemSaleDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ItemSaleConverterImpl implements GeneralConverter<Item, ItemSaleDTO> {
    @Override
    public ItemSaleDTO convertObjectToDTO(Item item) {
        ItemSaleDTO itemSaleDTO = new ItemSaleDTO();
        itemSaleDTO.setUuid(item.getUuid());
        itemSaleDTO.setUniqueNumber(item.getUniqueNumber());
        itemSaleDTO.setName(item.getName());
        ItemDetail itemDetail = item.getItemDetail();
        if (Objects.nonNull(itemDetail)) {
            itemSaleDTO.setPrice(itemDetail.getPrice());
        }
        itemSaleDTO.setDescription(item.getDescription());
        return itemSaleDTO;
    }

    @Override
    public Item convertDTOToObject(ItemSaleDTO object) {
        throw new UnsupportedOperationException("This method hasn't been implemented");
    }
}
