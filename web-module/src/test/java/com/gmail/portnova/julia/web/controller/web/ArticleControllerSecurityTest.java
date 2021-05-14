package com.gmail.portnova.julia.web.controller.web;

import com.gmail.portnova.julia.service.ArticleService;
import com.gmail.portnova.julia.service.CommentService;
import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.model.ArticleDTO;
import com.gmail.portnova.julia.service.model.PageDTO;
import com.gmail.portnova.julia.service.model.PageableArticle;
import com.gmail.portnova.julia.service.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ArticleController.class)
class ArticleControllerSecurityTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private ArticleService articleService;
    @MockBean
    private CommentService commentService;

    @MockBean
    private UserService userService;

    @WithMockUser(authorities = {"CUSTOMER_USER"})
    @Test
    void whenValidInputAndReturn200status() throws Exception {
        int pageNumber = 1;
        int maxResult = 10;
        Long totalPages = 1L;
        List<ArticleDTO> articles = Collections.emptyList();
        PageDTO<ArticleDTO> page = new PageableArticle();
        page.setTotalPages(totalPages);
        page.setObjects(articles);

        String email = "test@test.com";
        UserDTO user = new UserDTO();
        user.setEmail(email);
        when(userService.findUserByEmail(email)).thenReturn(user);

        when(articleService.getArticlesPage(pageNumber, maxResult)).thenReturn(page);

        mockMvc.perform(
                get("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @WithMockUser(authorities = {"ADMINISTRATOR"})
    @Test
    void whenValidInputAndUserHasRoleAdministratorThenReturnRedirection() throws Exception {
        mockMvc.perform(
                get("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is3xxRedirection());
    }
}