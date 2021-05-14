package com.gmail.portnova.julia.web.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.portnova.julia.service.ArticleApiService;
import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.impl.UserDetailsServiceImpl;
import com.gmail.portnova.julia.service.model.ArticleApiDTO;
import com.gmail.portnova.julia.service.model.UserDTO;
import com.gmail.portnova.julia.service.model.UserLogin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

}