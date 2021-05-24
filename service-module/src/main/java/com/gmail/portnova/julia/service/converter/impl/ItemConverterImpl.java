package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.Item;
import com.gmail.portnova.julia.repository.model.ItemDetail;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.ItemDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ItemConverterImpl implements GeneralConverter<Item, ItemDTO> {
    @Override
    public ItemDTO convertObjectToDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setUniqueNumber(item.getUniqueNumber());
        itemDTO.setUuid(item.getUuid());
        itemDTO.setName(item.getName());
        ItemDetail itemDetail = item.getItemDetail();
        if (Objects.nonNull(itemDetail)) {
            itemDTO.setPrice(itemDetail.getPrice());
        }
        return itemDTO;
    }

    @Override
    public Item convertDTOToObject(ItemDTO object) {
        throw new UnsupportedOperationException("This method hasn't been implemented");
    }
}
