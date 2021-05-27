package com.gmail.portnova.julia.web.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.portnova.julia.service.OrderApiService;
import com.gmail.portnova.julia.service.model.OrderApiDTO;
import com.gmail.portnova.julia.service.model.OrderDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderApiController.class,
        excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class)
@ActiveProfiles("test")
class OrderApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderApiService orderApiService;

    @Test
    void shouldGetAllOrders() throws Exception {
        mockMvc.perform(
                get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void shouldVerifyThatBusinessLogicWasCalledWhenWeRequestGetAllOrders() throws Exception {
        mockMvc.perform(
                get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(orderApiService, times(1)).getOrders();
    }

    @Test
    void shouldReturnCollectionsOfObjectsWhenWeRequestGetAllOrders() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        List<OrderDTO> orders = Collections.singletonList(orderDTO);
        when(orderApiService.getOrders()).thenReturn(orders);

        MvcResult mvcResult = mockMvc.perform(
                get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        assertThat(contentAsString).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(orders));
    }

    @Test
    void shouldGetOrderByUuid() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(
                get("/api/orders/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void shouldVerifyThatBusinessLogicWasCalledWhenWeRequestGetOrderByUuid() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(
                get("/api/orders/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(orderApiService, times(1)).getOrderApiByUuid(uuid.toString());
    }

    @Test
    void shouldReturnObjectWhenWeRequestGetOrderByUuid() throws Exception {
        OrderApiDTO orderDTO = new OrderApiDTO();
        UUID uuid = UUID.randomUUID();
        orderDTO.setOrderUuid(uuid.toString());

        when(orderApiService.getOrderApiByUuid(uuid.toString())).thenReturn(orderDTO);

        MvcResult mvcResult = mockMvc.perform(
                get("/api/orders/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        assertThat(contentAsString).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(orderDTO));
    }

}