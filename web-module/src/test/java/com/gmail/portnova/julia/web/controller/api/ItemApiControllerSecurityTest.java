package com.gmail.portnova.julia.web.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.portnova.julia.service.ItemApiService;
import com.gmail.portnova.julia.service.ItemService;
import com.gmail.portnova.julia.web.controller.api.config.TestUserDetailsConfig;
import com.gmail.portnova.julia.web.validator.ItemValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class,
        controllers = ItemApiController.class)
@Import(TestUserDetailsConfig.class)
class ItemApiControllerSecurityTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ItemService itemService;
    @MockBean
    private ItemApiService itemApiService;
    @MockBean
    private ItemValidator itemValidator;

    @Test
    void shouldUserWithRoleSecureRestApiGetAllItems() throws Exception {
        mockMvc.perform(
                get("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("api", "1234"))
        ).andExpect(status().isOk());
    }
    @Test
    void shouldNotGetAllItemsWhenUserEnterWrongPassword() throws Exception {
        mockMvc.perform(
                get("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("api", "1235"))
        ).andExpect(status().isUnauthorized());
    }
    @Test
    void shouldUserWithAdministratorNotGetAllItems() throws Exception {
        mockMvc.perform(
                get("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "1234"))
        ).andExpect(status().isForbidden());
    }
    @Test
    void shouldUserWithCustomerUserNotGetAllItems() throws Exception {
        mockMvc.perform(
                get("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("customer", "1234"))
        ).andExpect(status().isForbidden());
    }
    @Test
    void shouldUserWithSaleUserNotGetAllItems() throws Exception {
        mockMvc.perform(
                get("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("sale", "1234"))
        ).andExpect(status().isForbidden());
    }
}