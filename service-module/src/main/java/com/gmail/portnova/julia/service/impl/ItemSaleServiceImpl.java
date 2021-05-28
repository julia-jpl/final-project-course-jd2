package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.ItemRepository;
import com.gmail.portnova.julia.repository.model.Item;
import com.gmail.portnova.julia.service.ItemSaleService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.ItemNotFoundException;
import com.gmail.portnova.julia.service.model.ItemSaleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;

import static com.gmail.portnova.julia.service.constant.ExceptionMessageConstant.ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE;

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
        if (Objects.nonNull(item)) {
            ItemSaleDTO itemSaleDTO = itemSaleConverter.convertObjectToDTO(item);
            return itemSaleDTO;
        } else {
            throw new ItemNotFoundException(String.format(
                    ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE, Item.class, id));
        }
    }
}
