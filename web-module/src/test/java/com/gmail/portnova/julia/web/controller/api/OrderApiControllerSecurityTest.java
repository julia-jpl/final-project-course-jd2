package com.gmail.portnova.julia.web.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.portnova.julia.service.OrderApiService;
import com.gmail.portnova.julia.web.controller.api.config.TestUserDetailsConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("security")
@WebMvcTest(excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class,
        controllers = OrderApiController.class)
@Import(TestUserDetailsConfig.class)
class OrderApiControllerSecurityTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderApiService orderApiService;
    @Test
    void shouldUserWithRoleSecureRestApiGetAllOrders() throws Exception {
        mockMvc.perform(
                get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("api", "1234"))
        ).andExpect(status().isOk());
    }
    @Test
    void shouldNotGetAllOrdersWhenUserEnterWrongPassword() throws Exception {
        mockMvc.perform(
                get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("api", "1235"))
        ).andExpect(status().isUnauthorized());
    }
    @Test
    void shouldUserWithAdministratorNotGetAllOrders() throws Exception {
        mockMvc.perform(
                get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "1234"))
        ).andExpect(status().isForbidden());
    }
    @Test
    void shouldUserWithCustomerUserNotGetAllOrders() throws Exception {
        mockMvc.perform(
                get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("customer", "1234"))
        ).andExpect(status().isForbidden());
    }
    @Test
    void shouldUserWithSaleUserNotGetAllOrders() throws Exception {
        mockMvc.perform(
                get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("sale", "1234"))
        ).andExpect(status().isForbidden());
    }
}