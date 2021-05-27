package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.ItemRepository;
import com.gmail.portnova.julia.repository.model.Item;
import com.gmail.portnova.julia.service.ItemCustomerService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.ItemNotFoundException;
import com.gmail.portnova.julia.service.model.ItemCustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

import static com.gmail.portnova.julia.service.constant.ExceptionMessageConstant.ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE;

@Service
@RequiredArgsConstructor
public class ItemCustomerServiceImpl implements ItemCustomerService {
    private final ItemRepository itemRepository;
    private final GeneralConverter<Item, ItemCustomerDTO> itemCustomerConverter;

    @Override
    public ItemCustomerDTO findCustomerItemByUuid(String id) {
        UUID uuid = UUID.fromString(id);
        Item item = itemRepository.findByUuid(uuid);
        if (Objects.nonNull(item)) {
            return itemCustomerConverter.convertObjectToDTO(item);
        } else {
            throw new ItemNotFoundException(String.format(
                    ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE, Item.class, id));
        }
    }
}
