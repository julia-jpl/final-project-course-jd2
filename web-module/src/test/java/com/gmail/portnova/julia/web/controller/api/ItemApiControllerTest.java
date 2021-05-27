package com.gmail.portnova.julia.web.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.portnova.julia.service.ItemApiService;
import com.gmail.portnova.julia.service.ItemService;
import com.gmail.portnova.julia.service.exception.ItemNotFoundException;
import com.gmail.portnova.julia.service.model.ItemApiDTO;
import com.gmail.portnova.julia.service.model.ItemDTO;
import com.gmail.portnova.julia.web.validator.ItemValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemApiController.class,
        excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class)
@Import(ItemValidator.class)
@ActiveProfiles("test")
class ItemApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ItemService itemService;
    @MockBean
    private ItemApiService itemApiService;

    @Test
    void shouldGetAllItems() throws Exception {
        mockMvc.perform(
                get("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void shouldVerifyThatBusinessLogicWasCalledWhenWeRequestGetAllItems() throws Exception {
        mockMvc.perform(
                get("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(itemService, times(1)).getAllItems();
    }

    @Test
    void shouldReturnCollectionsOfObjectsWhenWeRequestGetAllItems() throws Exception {
        ItemDTO itemDTO = new ItemDTO();
        BigDecimal price = new BigDecimal(10.99);
        itemDTO.setPrice(price);
        String name = "name";
        itemDTO.setName(name);
        List<ItemDTO> items = Collections.singletonList(itemDTO);
        when(itemService.getAllItems()).thenReturn(items);

        MvcResult mvcResult = mockMvc.perform(
                get("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        assertThat(contentAsString).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(items));
    }

    @Test
    void shouldGetItemByUuid() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(
                get("/api/items/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void shouldVerifyThatBusinessLogicWasCalledWhenWeRequestGetItemByUuid() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(
                get("/api/items/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(itemApiService, times(1)).getItemApiByUuid(uuid.toString());
    }

    @Test
    void shouldReturnObjectWhenWeRequestGetItemByUuid() throws Exception {
        ItemApiDTO itemApiDTO = new ItemApiDTO();
        String name = "name";
        itemApiDTO.setName(name);
        UUID uuid = UUID.randomUUID();

        when(itemApiService.getItemApiByUuid(uuid.toString())).thenReturn(itemApiDTO);

        MvcResult mvcResult = mockMvc.perform(
                get("/api/items/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        assertThat(contentAsString).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(itemApiDTO));
    }

    @Test
    void shouldDeleteItem() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(
                delete("/api/items/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void shouldVerifyThatBusinessLogicWasCalledWhenWeRequestDeleteItemByUuid() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(
                delete("/api/items/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(itemApiService, times(1)).deleteItemByUuid(uuid.toString());
    }

    @Test
    void shouldAddItemWhenValidInput() throws Exception {
        ItemApiDTO itemApiDTO = new ItemApiDTO();
        String name = "name";
        itemApiDTO.setName(name);
        BigDecimal price = new BigDecimal(10.99);
        itemApiDTO.setPrice(price);
        String groupName = "ELECTRONICS";
        itemApiDTO.setItemGroup(groupName);
        UUID uuid = UUID.randomUUID();
        List<String> uuids = Collections.singletonList(uuid.toString());
        itemApiDTO.setUserUuids(uuids);
        String description = "description";
        itemApiDTO.setDescription(description);

        mockMvc.perform(
                post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemApiDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void verifyThatValidBusinessLogicCalledWithValidInputWhenWeRequestAddItem() throws Exception {
        ItemApiDTO itemApiDTO = new ItemApiDTO();
        String name = "name";
        itemApiDTO.setName(name);
        BigDecimal price = new BigDecimal(10.99);
        itemApiDTO.setPrice(price);
        String groupName = "ELECTRONICS";
        itemApiDTO.setItemGroup(groupName);
        UUID uuid = UUID.randomUUID();
        List<String> uuids = Collections.singletonList(uuid.toString());
        itemApiDTO.setUserUuids(uuids);
        String description = "description";
        itemApiDTO.setDescription(description);

        mockMvc.perform(
                post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemApiDTO)))
                .andExpect(status().isCreated());

        verify(itemApiService, times(1)).addItemToDatabase(itemApiDTO);
    }

    @Test
    void shouldReturn400whenWeRequestAddItemAndItemNameIsEmpty() throws Exception {
        ItemApiDTO itemApiDTO = new ItemApiDTO();
        String name = "";
        itemApiDTO.setName(name);
        BigDecimal price = new BigDecimal(10.99);
        itemApiDTO.setPrice(price);
        String groupName = "ELECTRONICS";
        itemApiDTO.setItemGroup(groupName);
        UUID uuid = UUID.randomUUID();
        List<String> uuids = Collections.singletonList(uuid.toString());
        itemApiDTO.setUserUuids(uuids);
        String description = "description";
        itemApiDTO.setDescription(description);

        mockMvc.perform(
                post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemApiDTO)))
                .andExpect(status().isBadRequest());
    }
}