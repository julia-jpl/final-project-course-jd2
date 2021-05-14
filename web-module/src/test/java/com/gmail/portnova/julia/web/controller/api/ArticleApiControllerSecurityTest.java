package com.gmail.portnova.julia.web.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.portnova.julia.service.ArticleApiService;
import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.exception.ArticleNotFoundException;
import com.gmail.portnova.julia.service.model.ArticleApiDTO;
import com.gmail.portnova.julia.service.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

@WebMvcTest(controllers = ArticleApiController.class)
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


    @WithMockUser(username = "rest@myhost.com", password = "qwer", authorities = {"SECURE_REST_API"})
    @Test
    void whenValidInput_thenReturns201() throws Exception {

        ArticleApiDTO articleApi = new ArticleApiDTO();
        String title = "title";
        articleApi.setTitle(title);
        String content = "content";
        articleApi.setContent(content);

        String email = "rest@myhost.com";
        UserDTO user = new UserDTO();
        user.setEmail(email);
        when(userService.findUserByEmail(email)).thenReturn(user);

        mockMvc.perform(
                post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleApi)))
                .andExpect(status().isCreated());
    }

    @WithMockUser(username = "rest@myhost.com", password = "qwer", authorities = {"SECURE_REST_API"})
    @Test
    void whenNotValidArticleContent_thenReturns400() throws Exception {

        ArticleApiDTO articleApi = new ArticleApiDTO();
        String title = "title";
        articleApi.setTitle(title);

        String email = "rest@myhost.com";
        UserDTO user = new UserDTO();
        user.setEmail(email);
        when(userService.findUserByEmail(email)).thenReturn(user);

        mockMvc.perform(
                post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleApi)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "rest@myhost.com", password = "qwer", authorities = {"SECURE_REST_API"})
    @Test
    void whenNotValidArticleTitle_thenReturns400() throws Exception {

        ArticleApiDTO articleApi = new ArticleApiDTO();
        String content = "content";
        articleApi.setContent(content);

        String email = "rest@myhost.com";
        UserDTO user = new UserDTO();
        user.setEmail(email);
        when(userService.findUserByEmail(email)).thenReturn(user);

        mockMvc.perform(
                post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleApi)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "rest@myhost.com", password = "qwer", authorities = {"SECURE_REST_API"})
    @Test
    void whenValidInput_thenCallBusinessLogic() throws Exception {

        ArticleApiDTO articleApi = new ArticleApiDTO();
        String title = "title";
        articleApi.setTitle(title);
        String content = "content";
        articleApi.setContent(content);

        String email = "rest@myhost.com";
        UUID uuid = UUID.randomUUID();
        UserDTO user = new UserDTO();
        user.setEmail(email);
        user.setUuid(uuid);

        when(userService.findUserByEmail(email)).thenReturn(user);

        ArticleApiDTO articleApiWithUserUuid = new ArticleApiDTO();
        articleApiWithUserUuid.setTitle(title);
        articleApiWithUserUuid.setContent(content);
        articleApiWithUserUuid.setUserUuid(user.getUuid());
        mockMvc.perform(
                post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleApi)))
                .andExpect(status().isCreated());

        verify(articleApiService, times(1)).addApiArticle(articleApiWithUserUuid);
    }

    @WithMockUser(authorities = {"SECURE_REST_API"})
    @Test
    void shouldDeleteArticle_AndReturn200status() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(
                delete("/api/articles/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @WithMockUser(authorities = {"SECURE_REST_API"})
    @Test
    void shouldDeleteArticleByUuid_AndReturn200status() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(
                delete("/api/articles/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @WithMockUser(authorities = {"SECURE_REST_API"})
    @Test
    void shouldNotDeleteArticleByUuid_AndReturn200status() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(articleApiService.deleteArticleApiByUuid(uuid.toString())).thenThrow(ArticleNotFoundException.class);

        mockMvc.perform(
                delete("/api/articles/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());

    }

    @WithMockUser(authorities = {"SECURE_REST_API"})
    @Test
    void shouldDeleteArticleByUuidAndCallBusinessLogic() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(
                delete("/api/articles/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(articleApiService, times(1)).deleteArticleApiByUuid(uuid.toString());
    }
}