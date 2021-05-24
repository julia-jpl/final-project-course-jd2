package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.ItemRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.repository.model.Item;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.ArticleDTO;
import com.gmail.portnova.julia.service.model.ItemDTO;
import com.gmail.portnova.julia.service.model.PageDTO;
import com.gmail.portnova.julia.service.model.PageableItem;
import com.gmail.portnova.julia.service.util.PageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.FetchProfile;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private GeneralConverter<Item, ItemDTO> itemConverter;
    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    void shouldGetAllItemsPage() {
        int pageNumber = 1;
        int maxResult = 10;
        Long numberOfRows = 1L;
        when(itemRepository.count()).thenReturn(numberOfRows);

        Long numberOfPages = PageUtil.getNumberOfPages(numberOfRows, maxResult);

        PageableItem pageDTO = new PageableItem();
        pageDTO.setTotalPages(numberOfPages);

        int startPosition = PageUtil.getStartPosition(pageNumber, maxResult);

        Item item = new Item();
        List<Item> items = Collections.singletonList(item);
        when(itemRepository.findAllWithLimit(startPosition, maxResult)).thenReturn(items);

        ItemDTO itemDTO = new ItemDTO();
        when(itemConverter.convertObjectToDTO(item)).thenReturn(itemDTO);
        List<ItemDTO> itemDTOS = itemService.getItemDTOList(items);

        pageDTO.getObjects().addAll(itemDTOS);

        PageDTO<ItemDTO> resultPage = itemService.getAllItemsPage(pageNumber, maxResult);

        assertEquals(numberOfPages, resultPage.getTotalPages());
        assertEquals(itemDTO, resultPage.getObjects().get(0));
    }

    @Test
    void shouldGetAllItems() {
        Item item = new Item();
        List<Item> items = Collections.singletonList(item);

        ItemDTO itemDTO = new ItemDTO();
        when(itemConverter.convertObjectToDTO(item)).thenReturn(itemDTO);
        List<ItemDTO> resultListItemDTOS = itemService.getItemDTOList(items);
        assertEquals(itemDTO, resultListItemDTOS.get(0));
    }
    @Test
    void deleteItemFromSaleUserCatalog(){

    }

}