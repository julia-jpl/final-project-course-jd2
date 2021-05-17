package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.ArticleRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.ArticleDTO;
import com.gmail.portnova.julia.service.model.ArticleUpdateDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.gmail.portnova.julia.service.constant.TimeFormatterConstant.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleUpdateServiceImplTest {
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private GeneralConverter<Article, ArticleDTO> articleConverter;
    @InjectMocks
    private ArticleUpdateServiceImpl articleUpdateService;

    @Test
    void shouldUpdateArticleTitleWhenContentIsEmpty() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        Article article = new Article();
        article.setUuid(uuid);
        when(articleRepository.findByUuid(uuid)).thenReturn(article);

        String newTitle = "title";
        ArticleUpdateDTO editedArticle = new ArticleUpdateDTO();
        editedArticle.setTitle(newTitle);
        String newContent = "";
        editedArticle.setContent(newContent);

        LocalDateTime updatedAt = LocalDateTime.now();
        article.setUpdatedAt(updatedAt);
        article.setTitle(newTitle);

        ArticleDTO savedArticle = new ArticleDTO();
        savedArticle.setTitle(newTitle);
        savedArticle.setUpdatedAt(updatedAt.format(DATE_TIME_FORMATTER));

        when(articleConverter.convertObjectToDTO(article)).thenReturn(savedArticle);

        ArticleDTO resultArticleDTO = articleUpdateService.updateArticle(editedArticle, id);
        assertNull(resultArticleDTO.getContent());
        assertEquals(newTitle, resultArticleDTO.getTitle());

    }
    @Test
    void shouldUpdateArticleContentWhenTitleIsEmpty() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        Article article = new Article();
        article.setUuid(uuid);
        when(articleRepository.findByUuid(uuid)).thenReturn(article);

        String newTitle = "";
        ArticleUpdateDTO editedArticle = new ArticleUpdateDTO();
        editedArticle.setTitle(newTitle);
        String newContent = "content";
        editedArticle.setContent(newContent);

        LocalDateTime updatedAt = LocalDateTime.now();
        article.setUpdatedAt(updatedAt);
        article.setContent(newContent);

        ArticleDTO savedArticle = new ArticleDTO();
        savedArticle.setContent(newContent);
        savedArticle.setUpdatedAt(updatedAt.format(DATE_TIME_FORMATTER));

        when(articleConverter.convertObjectToDTO(article)).thenReturn(savedArticle);

        ArticleDTO resultArticleDTO = articleUpdateService.updateArticle(editedArticle, id);
        assertNull(resultArticleDTO.getTitle());
        assertEquals(newContent, resultArticleDTO.getContent());
    }
}