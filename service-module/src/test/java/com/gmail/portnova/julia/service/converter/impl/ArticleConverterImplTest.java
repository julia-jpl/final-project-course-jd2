package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.model.ArticleApiDTO;
import com.gmail.portnova.julia.service.model.ArticleDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.gmail.portnova.julia.service.constant.TimeFormatterConstant.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleConverterImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ArticleConverterImpl articleConverter;

    @Test
    void shouldConvertArticleToArticleDTOAndReturnRightId() {
        Article article = new Article();
        Long id = 1L;
        article.setId(id);
        LocalDateTime createdAt = LocalDateTime.now();
        article.setCreatedAt(createdAt);

        ArticleDTO articleDTO = articleConverter.convertObjectToDTO(article);
        assertEquals(id, articleDTO.getId());
    }

    @Test
    void shouldConvertArticleToArticleDTOAndReturnRightUuid() {
        Article article = new Article();
        UUID uuid = UUID.randomUUID();
        article.setUuid(uuid);
        LocalDateTime createdAt = LocalDateTime.now();
        article.setCreatedAt(createdAt);

        ArticleDTO articleDTO = articleConverter.convertObjectToDTO(article);
        assertEquals(uuid, articleDTO.getUuid());
    }

    @Test
    void shouldConvertArticleToArticleDTOAndReturnRightTitle() {
        Article article = new Article();
        String title = "title";
        article.setTitle(title);
        LocalDateTime createdAt = LocalDateTime.now();
        article.setCreatedAt(createdAt);

        ArticleDTO articleDTO = articleConverter.convertObjectToDTO(article);
        assertEquals(title, articleDTO.getTitle());
    }

    @Test
    void shouldConvertArticleToArticleDTOAndReturnRightContent() {
        Article article = new Article();
        String content = "content";
        article.setContent(content);
        LocalDateTime createdAt = LocalDateTime.now();
        article.setCreatedAt(createdAt);

        ArticleDTO articleDTO = articleConverter.convertObjectToDTO(article);
        assertEquals(content, articleDTO.getContent());
    }

    @Test
    void shouldConvertArticleToArticleDTOAndReturnRightCreatedAtDate() {
        Article article = new Article();
        LocalDateTime dateTime = LocalDateTime.now();
        article.setCreatedAt(dateTime);
        String date = dateTime.format(DATE_TIME_FORMATTER);

        ArticleDTO articleDTO = articleConverter.convertObjectToDTO(article);
        assertEquals(date, articleDTO.getCreatedAt());
    }

    @Test
    void shouldConvertArticleToArticleDTOAndReturnRightUpdatedAtWhenDateIsNotNull() {
        Article article = new Article();
        LocalDateTime dateTime = LocalDateTime.now();
        article.setCreatedAt(dateTime);
        article.setUpdatedAt(dateTime);
        String date = dateTime.format(DATE_TIME_FORMATTER);

        ArticleDTO articleDTO = articleConverter.convertObjectToDTO(article);
        assertEquals(date, articleDTO.getUpdatedAt());
    }

    @Test
    void shouldConvertArticleToArticleDTOAndReturnRightUpdatedAtWhenDateIsNull() {
        Article article = new Article();
        LocalDateTime dateTime = LocalDateTime.now();
        article.setCreatedAt(dateTime);
        article.setUpdatedAt(null);

        ArticleDTO articleDTO = articleConverter.convertObjectToDTO(article);
        assertNull(articleDTO.getUpdatedAt());
    }

    @Test
    void shouldConvertArticleToArticleDTOAndReturnRightUserNameWhenUserIsNotNull() {
        Article article = new Article();
        User user = new User();
        String firstName = "first";
        user.setFirstName(firstName);
        String lastName = "last";
        user.setLastName(lastName);
        article.setUser(user);
        String lastAndFirstName = String.join(" ", lastName, firstName);
        LocalDateTime createdAt = LocalDateTime.now();
        article.setCreatedAt(createdAt);

        ArticleDTO articleDTO = articleConverter.convertObjectToDTO(article);
        assertEquals(lastAndFirstName, articleDTO.getUserLastAndFirstName());
    }


    @Test
    void shouldConvertArticleToArticleDTOAndReturnNotNullObject() {
        Article article = new Article();
        LocalDateTime createdAt = LocalDateTime.now();
        article.setCreatedAt(createdAt);

        ArticleDTO articleDTO = articleConverter.convertObjectToDTO(article);
        assertNotNull(articleDTO);
    }

    @Test
    void shouldConvertArticleToArticleDTOWhenArticleIsNull() {
        ArticleDTO articleDTO = articleConverter.convertObjectToDTO(null);
        assertNull(articleDTO);
    }

    @Test
    void shouldConvertArticleDTOToArticleWhenArticleDTOIsNull() {
        Article article = articleConverter.convertDTOToObject(null);
        assertNull(article);
    }

    @Test
    void shouldConvertArticleDTOToArticleAndReturnRightUuid() {
        ArticleDTO articleDTO = new ArticleDTO();
        UUID uuid = UUID.randomUUID();
        articleDTO.setUuid(uuid);

        articleDTO.setUserUuid(uuid);
        User user = new User();
        user.setUuid(uuid);

        when(userRepository.findByUuid(uuid)).thenReturn(user);

        Article article = articleConverter.convertDTOToObject(articleDTO);
        assertEquals(uuid, article.getUuid());
    }
    @Test
    void shouldConvertArticleDTOToArticleAndReturnRightTitle() {
        ArticleDTO articleDTO = new ArticleDTO();
        String title = "title";
        articleDTO.setTitle(title);

        UUID uuid = UUID.randomUUID();
        articleDTO.setUserUuid(uuid);
        User user = new User();
        user.setUuid(uuid);

        when(userRepository.findByUuid(uuid)).thenReturn(user);

        Article article = articleConverter.convertDTOToObject(articleDTO);
        assertEquals(title, article.getTitle());
    }
    @Test
    void shouldConvertArticleDTOToArticleAndReturnRightContent() {
        ArticleDTO articleDTO = new ArticleDTO();
        String content = "content";
        articleDTO.setContent(content);

        UUID uuid = UUID.randomUUID();
        articleDTO.setUserUuid(uuid);
        User user = new User();
        user.setUuid(uuid);

        when(userRepository.findByUuid(uuid)).thenReturn(user);

        Article article = articleConverter.convertDTOToObject(articleDTO);
        assertEquals(content, article.getContent());
    }
    @Test
    void shouldConvertArticleDTOToArticleAndReturnRightCreatedAtWhenDateIsNotNull() {
        ArticleDTO articleDTO = new ArticleDTO();
        LocalDateTime createdAt = LocalDateTime.now();
        String date = createdAt.format(DATE_TIME_FORMATTER);
        articleDTO.setCreatedAt(date);

        UUID uuid = UUID.randomUUID();
        articleDTO.setUserUuid(uuid);
        User user = new User();
        user.setUuid(uuid);

        when(userRepository.findByUuid(uuid)).thenReturn(user);

        Article article = articleConverter.convertDTOToObject(articleDTO);
        assertEquals(createdAt.format(DATE_TIME_FORMATTER), article.getCreatedAt().format(DATE_TIME_FORMATTER));
    }
    @Test
    void shouldConvertArticleDTOToArticleAndReturnRightCreatedAtWhenDateIsNull() {
        ArticleDTO articleDTO = new ArticleDTO();

        UUID uuid = UUID.randomUUID();
        articleDTO.setUserUuid(uuid);
        User user = new User();
        user.setUuid(uuid);

        when(userRepository.findByUuid(uuid)).thenReturn(user);

        Article article = articleConverter.convertDTOToObject(articleDTO);
        assertNull(article.getCreatedAt());
    }

    @Test
    void shouldConvertArticleDTOToArticleAndReturnRightAuthorWhenUserIsNotNull() {
        ArticleDTO articleDTO = new ArticleDTO();

        UUID uuid = UUID.randomUUID();
        articleDTO.setUserUuid(uuid);
        User user = new User();
        user.setUuid(uuid);
        String lastname = "last";
        user.setLastName(lastname);
        String firstname = "first";
        user.setFirstName(firstname);

        String author = String.join(" ", lastname, firstname);

        when(userRepository.findByUuid(uuid)).thenReturn(user);

        Article article = articleConverter.convertDTOToObject(articleDTO);
        assertEquals(author, article.getAuthor());
    }

    @Test
    void shouldConvertArticleDTOToArticleAndThrowExceptionWhenUserIsNull() {
        ArticleDTO articleDTO = new ArticleDTO();
        UUID uuid = UUID.randomUUID();
        articleDTO.setUserUuid(uuid);

        when(userRepository.findByUuid(uuid)).thenReturn(null);

        assertThrows(UserNotFoundException.class, ()-> articleConverter.convertDTOToObject(articleDTO));
    }
}