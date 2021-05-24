package com.gmail.portnova.julia.web.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.portnova.julia.service.ArticleApiService;
import com.gmail.portnova.julia.service.ArticleService;
import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.model.ArticleApiDTO;
import com.gmail.portnova.julia.service.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class,
        controllers = ArticleApiController.class)
class ArticleApiControllerSecurityTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ArticleApiService articleApiService;
    @MockBean
    private UserService userService;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private ArticleService articleService;

    @WithMockUser(authorities = {"SECURE_REST_API"})
    @Test
    void whenValidInputAndReturn200status() throws Exception {
        mockMvc.perform(
                get("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @WithMockUser(authorities = {"ADMINISTRATOR"})
    @Test
    void whenValidInputAndUserHasRoleAdministratorThenReturnForbidden() throws Exception {
        mockMvc.perform(
                get("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = {"CUSTOMER_USER"})
    @Test
    void whenValidInputAndUserHasRoleCustomerUserThenReturnForbidden() throws Exception {
        mockMvc.perform(
                get("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = {"SALE_USER"})
    @Test
    void whenValidInputAndUserHasRoleSaleUserThenReturnForbidden() throws Exception {
        mockMvc.perform(
                get("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden());
    }
}