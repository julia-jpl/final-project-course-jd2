package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.ItemRepository;
import com.gmail.portnova.julia.repository.model.Item;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.ItemNotFoundException;
import com.gmail.portnova.julia.service.model.ItemApiDTO;
import com.gmail.portnova.julia.service.model.ItemSaleDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemSaleServiceImplTest {
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private GeneralConverter<Item, ItemSaleDTO> itemSaleConverter;
    @InjectMocks
    private ItemSaleServiceImpl itemSaleService;

    @Test
    void shouldGetItemApiByUuid() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        Item item = new Item();
        item.setUuid(uuid);
        when(itemRepository.findByUuid(uuid)).thenReturn(item);

        ItemSaleDTO itemSaleDTO = new ItemSaleDTO();
        itemSaleDTO.setUuid(uuid);
        when(itemSaleConverter.convertObjectToDTO(item)).thenReturn(itemSaleDTO);

        ItemSaleDTO result = itemSaleService.findByUuid(id);
        assertEquals(uuid, result.getUuid());
    }

    @Test
    void shouldNotGetItemByUUID() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);

        when(itemRepository.findByUuid(uuid)).thenReturn(null);
        assertThrows(ItemNotFoundException.class, () -> itemSaleService.findByUuid(id));
    }

}