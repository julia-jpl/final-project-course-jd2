package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.ItemRepository;
import com.gmail.portnova.julia.repository.model.Item;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.ItemNotFoundException;
import com.gmail.portnova.julia.service.model.ItemApiDTO;
import com.gmail.portnova.julia.service.model.ItemGroupNameEnumDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemApiServiceImplTest {
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private GeneralConverter<Item, ItemApiDTO> itemApiConverter;
    @InjectMocks
    private ItemApiServiceImpl itemApiService;

    @Test
    void shouldGetItemApiByUuid() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        Item item = new Item();
        item.setUuid(uuid);
        when(itemRepository.findByUuid(uuid)).thenReturn(item);

        ItemApiDTO itemApiDTO = new ItemApiDTO();
        itemApiDTO.setUuid(uuid);
        when(itemApiConverter.convertObjectToDTO(item)).thenReturn(itemApiDTO);

        ItemApiDTO result = itemApiService.getItemApiByUuid(id);
        assertEquals(uuid, result.getUuid());
    }

    @Test
    void shouldNotGetItemByUUID() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);

        when(itemRepository.findByUuid(uuid)).thenReturn(null);
        assertThrows(ItemNotFoundException.class, () -> itemApiService.getItemApiByUuid(id));
    }

    @Test
    void shouldDeleteItemByUuid() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        Item item = new Item();
        item.setUuid(uuid);
        when(itemRepository.findByUuid(uuid)).thenReturn(item);

        ItemApiDTO itemApiDTO = new ItemApiDTO();
        itemApiDTO.setUuid(uuid);
        when(itemApiConverter.convertObjectToDTO(item)).thenReturn(itemApiDTO);

        ItemApiDTO result = itemApiService.deleteItemByUuid(id);
        assertEquals(uuid, result.getUuid());
    }

    @Test
    void shouldNotDeleteItemByUUIDAndThrowExceptionWhenItemIsNull() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);

        when(itemRepository.findByUuid(uuid)).thenReturn(null);

        assertThrows(ItemNotFoundException.class, () -> itemApiService.deleteItemByUuid(id));
    }

    @Test
    void shouldAddItemWithGeneratedFieldsToDatabase() {
        ItemApiDTO enterItemDTO = new ItemApiDTO();
        String itemGroup = ItemGroupNameEnumDTO.ELECTRONICS.name();
        enterItemDTO.setItemGroup(itemGroup);

        ItemApiDTO itemApiDTO = itemApiService.addItemToDatabase(enterItemDTO);
        assertNotNull(itemApiDTO.getUuid());
        assertNotNull(itemApiDTO.getUniqueNumber());
    }
}