package com.gmail.portnova.julia.web.controller.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.portnova.julia.service.ProfileService;
import com.gmail.portnova.julia.service.UserAddService;
import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.model.ProfileUserDTO;
import com.gmail.portnova.julia.service.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProfileController.class)
class ProfileControllerSecurityTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserAddService userAddService;
    @MockBean
    private ProfileService profileService;
    @MockBean
    private UserService userService;

    @WithMockUser(authorities = {"CUSTOMER_USER"})
    @Test
    void whenUserWithRoleCustomerUserTryToGetAccessThenReturn200status() throws Exception {
        UUID uuid = UUID.randomUUID();
        String lastname = "lastname";
        String firstname = "firstname";
        ProfileUserDTO userProfile = new ProfileUserDTO();
        userProfile.setUuid(uuid);
        userProfile.setLastName(lastname);
        userProfile.setFirstName(firstname);


        when(profileService.getUserProfile(uuid)).thenReturn(userProfile);

        mockMvc.perform(
                get("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(model().attribute("userProfile", userProfile));
    }

    @WithMockUser(authorities = {"ADMINISTRATOR"})
    @Test
    void whenUserHasRoleAdministratorTryToGetAccessToProfileThenReturnRedirected() throws Exception {
        UUID uuid = UUID.randomUUID();
        ProfileUserDTO profile = new ProfileUserDTO();
        profile.setUuid(uuid);


        when(profileService.getUserProfile(uuid)).thenReturn(profile);

        mockMvc.perform(
                get("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is3xxRedirection());
    }

    @WithMockUser(authorities = {"SECURE_REST_API"})
    @Test
    void whenValidInputAndUserHasRoleSecureRestApiAndReturnForbidden() throws Exception {
        UUID uuid = UUID.randomUUID();
        ProfileUserDTO profile = new ProfileUserDTO();
        profile.setUuid(uuid);

        when(profileService.getUserProfile(uuid)).thenReturn(profile);

        mockMvc.perform(
                get("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is3xxRedirection());
    }
}