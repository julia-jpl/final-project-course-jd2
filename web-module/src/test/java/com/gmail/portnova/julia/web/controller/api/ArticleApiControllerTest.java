package com.gmail.portnova.julia.web.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.portnova.julia.service.ArticleApiService;
import com.gmail.portnova.julia.service.ArticleService;
import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.exception.ArticleNotFoundException;
import com.gmail.portnova.julia.service.model.ArticleApiDTO;
import com.gmail.portnova.julia.service.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ArticleApiController.class)
@ActiveProfiles("test")
class ArticleApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ArticleApiService articleApiService;
    @MockBean
    private UserService userService;
    @MockBean
    private ArticleService articleService;


    @Test
    void shouldGetArticles() throws Exception {
        mockMvc.perform(
                get("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void shouldVerifyThatBusinessLogicWasCalledWhenWeRequestGetArticles() throws Exception {
        mockMvc.perform(
                get("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(articleApiService, times(1)).getAllArticles();
    }

    @Test
    void shouldReturnCollectionsOfObjectsWhenWeRequestGetArticles() throws Exception {
        ArticleApiDTO article = new ArticleApiDTO();
        article.setId(1L);
        String title = "Title";
        article.setTitle(title);
        List<ArticleApiDTO> articles = Collections.singletonList(article);
        when(articleApiService.getAllArticles()).thenReturn(articles);

        MvcResult mvcResult = mockMvc.perform(
                get("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        assertThat(contentAsString).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(articles));
    }

    @Test
    void shouldGetArticleByUuid() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(
                get("/api/articles/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void shouldVerifyThatBusinessLogicWasCalledWhenWeRequestGetArticleByUuid() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(
                get("/api/articles/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(articleApiService, times(1)).findApiByUuid(uuid.toString());
    }

    @Test
    void shouldReturnObjectWhenWeRequestGetArticleByUuid() throws Exception {
        ArticleApiDTO article = new ArticleApiDTO();
        article.setId(1L);
        UUID uuid = UUID.randomUUID();

        when(articleApiService.findApiByUuid(uuid.toString())).thenReturn(article);

        MvcResult mvcResult = mockMvc.perform(
                get("/api/articles/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        assertThat(contentAsString).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(article));
    }

    @Test
    void shouldDeleteArticleByUuid_AndReturn200status() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(
                delete("/api/articles/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
    @Test
    void shouldDeleteArticleByUuidAndCallBusinessLogic() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(
                delete("/api/articles/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(articleService, times(1)).deleteArticleByUuid(uuid.toString());
    }

    @Test
    void shouldNotDeleteArticleByUuid_AndReturn404status() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(articleService.deleteArticleByUuid(uuid.toString())).thenThrow(ArticleNotFoundException.class);

        mockMvc.perform(
                delete("/api/articles/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
    @WithMockUser(username = "rest@myhost.com", password = "qwer", authorities = {"SECURE_REST_API"})
    @Test
    void whenValidInput_thenReturn201() throws Exception {

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
    void whenNotValidArticleContent_thenReturn400() throws Exception {

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
    void whenNotValidArticleTitle_thenReturn400() throws Exception {

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
}