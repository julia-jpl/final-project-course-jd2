package com.gmail.portnova.julia.web.controller.web;

import com.gmail.portnova.julia.service.RoleService;
import com.gmail.portnova.julia.service.UserAddService;
import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.web.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerSecurityTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private UserAddService userAddService;
    @MockBean
    private RoleService roleService;
    @MockBean
    private UserValidator userValidator;

    @WithMockUser(authorities = {"ADMINISTRATOR"})
    @Test
    void whenValidInputAndReturn200status() throws Exception {
        mockMvc.perform(
                get("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @WithMockUser(authorities = {"CUSTOMER_USER"})
    @Test
    void whenValidInputAndReturnRedirection() throws Exception {
        mockMvc.perform(
                get("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is3xxRedirection());
    }

    @WithMockUser(authorities = {"SECURE_REST_API"})
    @Test
    void whenValidInputAndUserHasRoleSecureRestApiThenReturnRedirection() throws Exception {
        mockMvc.perform(
                get("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is3xxRedirection());
    }
}