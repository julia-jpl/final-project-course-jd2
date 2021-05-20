package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.ItemRepository;
import com.gmail.portnova.julia.repository.model.Item;
import com.gmail.portnova.julia.service.ItemSaleService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.ItemSaleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemSaleServiceImpl implements ItemSaleService {
    private final ItemRepository itemRepository;
    private final GeneralConverter<Item, ItemSaleDTO> itemSaleConverter;

    @Transactional
    @Override
    public ItemSaleDTO findByUuid(String id) {
        UUID itemUuid = UUID.fromString(id);
        Item item = itemRepository.findByUuid(itemUuid);
        ItemSaleDTO itemSaleDTO = itemSaleConverter.convertObjectToDTO(item);
        return itemSaleDTO;
    }
}
