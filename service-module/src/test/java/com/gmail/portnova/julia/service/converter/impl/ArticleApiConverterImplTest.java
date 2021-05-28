package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.model.ArticleApiDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleApiConverterImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ArticleApiConverterImpl articleApiConverter;

    @Test
    void shouldConvertArticleApiDTOtoArticleAndReturnRightUuid() {
        ArticleApiDTO articleApiDTO = new ArticleApiDTO();
        UUID uuid = UUID.randomUUID();
        UUID userUuid = UUID.randomUUID();
        articleApiDTO.setUuid(uuid);
        articleApiDTO.setUserUuid(userUuid);
        User user = new User();
        user.setUuid(userUuid);
        when(userRepository.findByUuid(userUuid)).thenReturn(user);

        Article article = articleApiConverter.convertDTOToObject(articleApiDTO);
        assertEquals(uuid, article.getUuid());
    }
    @Test
    void shouldConvertArticleApiDTOtoArticleAndReturnRightTitle() {
        ArticleApiDTO articleApiDTO = new ArticleApiDTO();
        String title = "title";
        articleApiDTO.setTitle(title);

        UUID userUuid = UUID.randomUUID();
        articleApiDTO.setUserUuid(userUuid);
        User user = new User();
        user.setUuid(userUuid);
        when(userRepository.findByUuid(userUuid)).thenReturn(user);

        Article article = articleApiConverter.convertDTOToObject(articleApiDTO);
        assertEquals(title, article.getTitle());
    }
    @Test
    void shouldConvertArticleApiDTOtoArticleAndReturnRightContent() {
        ArticleApiDTO articleApiDTO = new ArticleApiDTO();
        String content = "content";
        articleApiDTO.setContent(content);
        UUID userUuid = UUID.randomUUID();
        articleApiDTO.setUserUuid(userUuid);
        User user = new User();
        user.setUuid(userUuid);
        when(userRepository.findByUuid(userUuid)).thenReturn(user);

        Article article = articleApiConverter.convertDTOToObject(articleApiDTO);
        assertEquals(content, article.getContent());
    }

    @Test
    void shouldConvertArticleApiDTOtoArticleAndReturnRightCreatedAtDate() {
        ArticleApiDTO articleApiDTO = new ArticleApiDTO();
        LocalDateTime date = LocalDateTime.now();
        articleApiDTO.setCreatedAt(date);
        UUID userUuid = UUID.randomUUID();
        articleApiDTO.setUserUuid(userUuid);
        User user = new User();
        user.setUuid(userUuid);
        when(userRepository.findByUuid(userUuid)).thenReturn(user);

        Article article = articleApiConverter.convertDTOToObject(articleApiDTO);
        assertEquals(date, article.getCreatedAt());
    }

    @Test
    void shouldConvertArticleApiDTOtoArticleAndReturnRightUser() {
        ArticleApiDTO articleApiDTO = new ArticleApiDTO();
        UUID uuid = UUID.randomUUID();
        articleApiDTO.setUserUuid(uuid);

        User user = new User();
        user.setUuid(uuid);

        when(userRepository.findByUuid(uuid)).thenReturn(user);

        Article article = articleApiConverter.convertDTOToObject(articleApiDTO);
        assertEquals(user.getUuid(), article.getUser().getUuid());
    }
    @Test
    void shouldConvertArticleApiDTOtoArticleAndReturnNotNullArticle() {
        ArticleApiDTO articleApiDTO = new ArticleApiDTO();
        UUID userUuid = UUID.randomUUID();
        articleApiDTO.setUserUuid(userUuid);
        User user = new User();
        user.setUuid(userUuid);
        when(userRepository.findByUuid(userUuid)).thenReturn(user);
        Article article = articleApiConverter.convertDTOToObject(articleApiDTO);
        assertNotNull(article);
    }

    @Test
    void shouldConvertArticleToArticleApiDTOAndReturnRightUuid() {
        Article article = new Article();
        UUID uuid = UUID.randomUUID();
        article.setUuid(uuid);

        ArticleApiDTO articleApiDTO = articleApiConverter.convertObjectToDTO(article);
        assertEquals(uuid, articleApiDTO.getUuid());
    }

    @Test
    void shouldConvertArticleToArticleApiDTOAndReturnRightTitle() {
        Article article = new Article();
        String title = "title";;
        article.setTitle(title);

        ArticleApiDTO articleApiDTO = articleApiConverter.convertObjectToDTO(article);
        assertEquals(title, articleApiDTO.getTitle());
    }
    @Test
    void shouldConvertArticleToArticleApiDTOAndReturnRightContent() {
        Article article = new Article();
        String content = "content";;
        article.setContent(content);

        ArticleApiDTO articleApiDTO = articleApiConverter.convertObjectToDTO(article);
        assertEquals(content, articleApiDTO.getContent());
    }

    @Test
    void shouldConvertArticleToArticleApiDTOAndReturnRightCreatedAtDate() {
        Article article = new Article();
        LocalDateTime dateTime = LocalDateTime.now();
        article.setCreatedAt(dateTime);

        ArticleApiDTO articleApiDTO = articleApiConverter.convertObjectToDTO(article);
        assertEquals(dateTime, articleApiDTO.getCreatedAt());
    }
    @Test
    void shouldConvertArticleToArticleApiDTOAndReturnRightUserUuid() {
        Article article = new Article();
        User user = new User();
        UUID uuid = UUID.randomUUID();
        user.setUuid(uuid);
        article.setUser(user);

        ArticleApiDTO articleApiDTO = articleApiConverter.convertObjectToDTO(article);
        assertEquals(uuid, articleApiDTO.getUserUuid());
    }
    @Test
    void shouldConvertArticleToArticleApiDTOAndReturnNotNullObject() {
        Article article = new Article();

        ArticleApiDTO articleApiDTO = articleApiConverter.convertObjectToDTO(article);
        assertNotNull(articleApiDTO);
    }

    @Test
    void shouldConvertArticleToArticleApiDTOWhenArticleIsNull() {

        ArticleApiDTO articleApiDTO = articleApiConverter.convertObjectToDTO(null);
        assertNull(articleApiDTO);
    }
}