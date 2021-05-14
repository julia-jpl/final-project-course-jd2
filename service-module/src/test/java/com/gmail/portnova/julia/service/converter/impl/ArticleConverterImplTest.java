package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.model.ArticleApiDTO;
import com.gmail.portnova.julia.service.model.ArticleDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ArticleConverterImplTest {
    private final ArticleConverterImpl articleConverter = new ArticleConverterImpl();

    @Test
    void shouldConvertArticleToArticleDTOAndReturnRightId() {
        Article article = new Article();
        Long id = 1L;
        article.setId(id);

        ArticleDTO articleDTO = articleConverter.convertObjectToDTO(article);
        assertEquals(id, articleDTO.getId());
    }

    @Test
    void shouldConvertArticleToArticleDTOAndReturnRightUuid() {
        Article article = new Article();
        UUID uuid = UUID.randomUUID();
        article.setUuid(uuid);

        ArticleDTO articleDTO = articleConverter.convertObjectToDTO(article);
        assertEquals(uuid, articleDTO.getUuid());
    }

    @Test
    void shouldConvertArticleToArticleDTOAndReturnRightTitle() {
        Article article = new Article();
        String title = "title";
        article.setTitle(title);

        ArticleDTO articleDTO = articleConverter.convertObjectToDTO(article);
        assertEquals(title, articleDTO.getTitle());
    }

    @Test
    void shouldConvertArticleToArticleDTOAndReturnRightContent() {
        Article article = new Article();
        String content = "content";
        article.setContent(content);

        ArticleDTO articleDTO = articleConverter.convertObjectToDTO(article);
        assertEquals(content, articleDTO.getContent());
    }

    @Test
    void shouldConvertArticleToArticleDTOAndReturnRightCreatedAtDate() {
        Article article = new Article();
        LocalDateTime dateTime = LocalDateTime.now();
        article.setCreatedAt(dateTime);

        ArticleDTO articleDTO = articleConverter.convertObjectToDTO(article);
        assertEquals(dateTime, articleDTO.getCreatedAt());
    }

    @Test
    void shouldConvertArticleToArticleDTOAndReturnRightUserName() {
        Article article = new Article();
        User user = new User();
        String firstName = "first";
        user.setFirstName(firstName);
        String lastName = "last";
        user.setLastName(lastName);
        article.setUser(user);
        String lastAndFirstName = String.join(" ", lastName, firstName);

        ArticleDTO articleDTO = articleConverter.convertObjectToDTO(article);
        assertEquals(lastAndFirstName, articleDTO.getUserLastAndFirstName());
    }

    @Test
    void shouldConvertArticleToArticleDTOAndReturnNotNullObject() {
        Article article = new Article();

        ArticleDTO articleDTO = articleConverter.convertObjectToDTO(article);
        assertNotNull(articleDTO);
    }

    @Test
    void shouldConvertArticleToArticleDTOWhenArticleIsNull() {

        ArticleDTO articleDTO = articleConverter.convertObjectToDTO(null);
        assertNull(articleDTO);
    }
}