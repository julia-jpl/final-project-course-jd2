package com.gmail.portnova.julia.web.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.portnova.julia.service.UserAddService;
import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.model.RoleNameEnumDTO;
import com.gmail.portnova.julia.service.model.UserDTO;
import com.gmail.portnova.julia.web.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserApiController.class)
class UserApiControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserAddService userAddService;
    @MockBean
    private UserValidator userValidator;
    @MockBean
    private UserService userService;

    @WithMockUser(username = "rest@myhost.com", password = "qwer", authorities = {"SECURE_REST_API"})
    @Test
    void whenValidInput_thenReturns201() throws Exception {
        UserDTO user = new UserDTO();
        String lastName = "test";
        user.setLastName(lastName);
        String firstName = "test";
        user.setFirstName(firstName);
        String middleName = "test";
        user.setMiddleName(middleName);
        String email = "test@test.com";
        user.setEmail(email);
        String roleName = RoleNameEnumDTO.ADMINISTRATOR.name();
        user.setRoleName(roleName);

        mockMvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @WithMockUser(username = "rest@myhost.com", password = "qwer", authorities = {"SECURE_REST_API"})
    @Test
    void whenNotValidUsername() throws Exception {
        UserDTO user = new UserDTO();
        String lastName = "test";
        user.setLastName(lastName);
        String firstName = "test";
        user.setFirstName(firstName);
        String middleName = "test";
        user.setMiddleName(middleName);
        String email = "test";
        user.setEmail(email);
        String roleName = RoleNameEnumDTO.ADMINISTRATOR.name();
        user.setRoleName(roleName);

        UserValidator userValidatorForTest = new UserValidator(userService);
        BindException errors = new BindException(user, "user");
        ValidationUtils.invokeValidator(userValidatorForTest, user, errors);

        assertTrue(errors.hasErrors());
    }

    @WithMockUser(username = "rest@myhost.com", password = "qwer", authorities = {"SECURE_REST_API"})
    @Test
    void whenValidInputAndBusinessLogicCalled() throws Exception {
        UserDTO user = new UserDTO();
        String lastName = "test";
        user.setLastName(lastName);
        String firstName = "test";
        user.setFirstName(firstName);
        String middleName = "test";
        user.setMiddleName(middleName);
        String email = "test@test.com";
        user.setEmail(email);
        String roleName = RoleNameEnumDTO.ADMINISTRATOR.name();
        user.setRoleName(roleName);

        mockMvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());

        verify(userAddService, times(1)).addUser(user);
    }
}