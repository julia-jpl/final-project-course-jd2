package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.ArticleRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.ArticleNotFoundException;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.model.ArticleApiDTO;
import com.gmail.portnova.julia.service.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleApiServiceImplTest {
    @Mock
    private GeneralConverter<Article, ArticleApiDTO> articleApiConverter;
    @Mock
    private ArticleRepository articleRepository;
    @InjectMocks
    private ArticleApiServiceImpl articleApiService;

    @Test
    void shouldGetAllArticles() {
        Article article = new Article();
        List<Article> articles = Collections.singletonList(article);

        when(articleRepository.findAll()).thenReturn(articles);

        ArticleApiDTO articleApiDTO = new ArticleApiDTO();
        when(articleApiConverter.convertObjectToDTO(article)).thenReturn(articleApiDTO);
        List<ArticleApiDTO> articleApiDTOS = Collections.singletonList(articleApiDTO);

        List<ArticleApiDTO> resultArticlesApiDTO = articleApiService.getAllArticles();

        assertEquals(articleApiDTOS.size(), resultArticlesApiDTO.size());
        assertEquals(articleApiDTOS.get(0), resultArticlesApiDTO.get(0));
    }

    @Test
    void shouldFindApiByUuid() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        Article article = new Article();
        article.setUuid(uuid);
        when(articleRepository.findByUuid(uuid)).thenReturn(article);

        ArticleApiDTO articleApiDTO = new ArticleApiDTO();
        articleApiDTO.setUuid(uuid);
        when(articleApiConverter.convertObjectToDTO(article)).thenReturn(articleApiDTO);

        ArticleApiDTO result = articleApiService.findApiByUuid(id);
        assertEquals(uuid, result.getUuid());
    }
    @Test
    void shouldNotFindByUUID() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);

        when(articleRepository.findByUuid(uuid)).thenReturn(null);
        assertThrows(ArticleNotFoundException.class, () -> articleApiService.findApiByUuid(id));
    }

}