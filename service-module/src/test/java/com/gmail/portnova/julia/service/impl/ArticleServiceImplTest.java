package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.ArticleRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.ArticleNotFoundException;
import com.gmail.portnova.julia.service.model.ArticleDTO;
import com.gmail.portnova.julia.service.model.PageDTO;
import com.gmail.portnova.julia.service.model.PageableArticle;
import com.gmail.portnova.julia.service.util.PageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private GeneralConverter<Article, ArticleDTO> articleConverter;
    @InjectMocks
    private ArticleServiceImpl articleService;

    @Test
    void shouldFindArticleByUuid() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        Article article = new Article();
        article.setUuid(uuid);
        when(articleRepository.findByUuid(uuid)).thenReturn(article);

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setUuid(uuid);
        when(articleConverter.convertObjectToDTO(article)).thenReturn(articleDTO);

        ArticleDTO result = articleService.findByUuid(id);
        assertEquals(uuid, result.getUuid());
    }

    @Test
    void shouldNotFindArticleByUUID() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);

        when(articleRepository.findByUuid(uuid)).thenReturn(null);
        assertThrows(ArticleNotFoundException.class, () -> articleService.findByUuid(id));
    }

    @Test
    void shouldGetArticlesPage() {
        int pageNumber = 1;
        int maxResult = 10;
        Long numberOfRows = 1L;
        when(articleRepository.count()).thenReturn(numberOfRows);

        Long numberOfPages = PageUtil.getNumberOfPages(numberOfRows, maxResult);

        PageableArticle pageDTO = new PageableArticle();
        pageDTO.setTotalPages(numberOfPages);

        int startPosition = PageUtil.getStartPosition(pageNumber, maxResult);

        Article article = new Article();
        List<Article> articles = Collections.singletonList(article);
        when(articleRepository.findAllWithLimit(startPosition, maxResult)).thenReturn(articles);

        ArticleDTO articleDTO = new ArticleDTO();
        when(articleConverter.convertObjectToDTO(article)).thenReturn(articleDTO);
        List<ArticleDTO> articleDTOS = articleService.getArticleDTOList(articles);

        pageDTO.getObjects().addAll(articleDTOS);

        PageableArticle resultPage = articleService.getArticlesPage(pageNumber, maxResult);

        assertEquals(numberOfPages, resultPage.getTotalPages());
        assertEquals(articleDTO, resultPage.getObjects().get(0));
    }

    @Test
    void shouldGetArticleDTOList() {
        Article article = new Article();
        List<Article> articles = Collections.singletonList(article);

        ArticleDTO articleDTO = new ArticleDTO();
        when(articleConverter.convertObjectToDTO(article)).thenReturn(articleDTO);
        List<ArticleDTO> resultListArticleDTOS = articleService.getArticleDTOList(articles);
        assertEquals(articleDTO, resultListArticleDTOS.get(0));
    }

    @Test
    void shouldGetArticleDTOEmptyList() {
        List<Article> articles = Collections.emptyList();

        List<ArticleDTO> resultListArticleDTOS = articleService.getArticleDTOList(articles);
        assertEquals(Collections.emptyList(), resultListArticleDTOS);
    }

    @Test
    void shouldDeleteArticleByUuid() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        Article article = new Article();
        article.setUuid(uuid);
        when(articleRepository.findByUuid(uuid)).thenReturn(article);

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setUuid(uuid);
        when(articleConverter.convertObjectToDTO(article)).thenReturn(articleDTO);

        ArticleDTO result = articleService.deleteArticleByUuid(id);
        assertEquals(uuid, result.getUuid());
    }

    @Test
    void shouldNotDeleteArticleByUUID() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);

        when(articleRepository.findByUuid(uuid)).thenReturn(null);
        assertThrows(ArticleNotFoundException.class, () -> articleService.deleteArticleByUuid(id));
    }

    @Test
    void shouldGetSaleUserArticlesPage() {
        int pageNumber = 1;
        int maxResult = 10;
        Long numberOfRows = 1L;
        UUID uuid = UUID.randomUUID();
        when(articleRepository.countArticleByUserUuid(uuid)).thenReturn(numberOfRows);

        Long numberOfPages = PageUtil.getNumberOfPages(numberOfRows, maxResult);

        PageableArticle pageDTO = new PageableArticle();
        pageDTO.setTotalPages(numberOfPages);

        int startPosition = PageUtil.getStartPosition(pageNumber, maxResult);

        Article article = new Article();
        List<Article> articles = Collections.singletonList(article);
        when(articleRepository.findArticlesWithLimitByUserUuid(startPosition, maxResult, uuid)).thenReturn(articles);

        ArticleDTO articleDTO = new ArticleDTO();
        when(articleConverter.convertObjectToDTO(article)).thenReturn(articleDTO);
        List<ArticleDTO> articleDTOS = articleService.getArticleDTOList(articles);

        pageDTO.getObjects().addAll(articleDTOS);

        PageDTO<ArticleDTO> resultPage = articleService.getSaleUserArticlesPage(pageNumber, maxResult, uuid);

        assertEquals(numberOfPages, resultPage.getTotalPages());
        assertEquals(articleDTO, resultPage.getObjects().get(0));
    }

}
