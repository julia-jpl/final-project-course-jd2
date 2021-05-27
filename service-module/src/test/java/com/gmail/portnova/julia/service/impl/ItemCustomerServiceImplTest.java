package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.ItemRepository;
import com.gmail.portnova.julia.repository.model.Item;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.ItemNotFoundException;
import com.gmail.portnova.julia.service.model.ItemCustomerDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemCustomerServiceImplTest {
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private GeneralConverter<Item, ItemCustomerDTO> itemCustomerConverter;
    @InjectMocks
    private ItemCustomerServiceImpl itemCustomerService;

    @Test
    void shouldFindCustomerItemByUuid() {
        UUID itemUuid = UUID.randomUUID();
        Item item = new Item();
        item.setUuid(itemUuid);

        when(itemRepository.findByUuid(itemUuid)).thenReturn(item);
        ItemCustomerDTO itemCustomerDTO = new ItemCustomerDTO();
        itemCustomerDTO.setUuid(itemUuid);

        when(itemCustomerConverter.convertObjectToDTO(item)).thenReturn(itemCustomerDTO);

        ItemCustomerDTO customerItemResult = itemCustomerService.findCustomerItemByUuid(itemUuid.toString());
        assertEquals(itemUuid, customerItemResult.getUuid());
    }
    @Test
    void shouldNotFindCustomerItemByUuidAndthrowException() {
        UUID itemUuid = UUID.randomUUID();

        when(itemRepository.findByUuid(itemUuid)).thenReturn(null);
        assertThrows(ItemNotFoundException.class, ()-> itemCustomerService.findCustomerItemByUuid(itemUuid.toString()));
    }
}