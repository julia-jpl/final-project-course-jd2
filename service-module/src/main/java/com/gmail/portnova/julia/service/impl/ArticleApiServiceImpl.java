package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.ArticleRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.service.ArticleApiService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.ArticleNotFoundException;
import com.gmail.portnova.julia.service.model.ArticleApiDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleApiServiceImpl implements ArticleApiService {
    private final GeneralConverter<Article, ArticleApiDTO> articleApiConverter;
    private final ArticleRepository articleRepository;

    @Override
    @Transactional
    public List<ArticleApiDTO> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleApiDTO> articlesApiDTO = articles.stream()
                .map(articleApiConverter::convertObjectToDTO)
                .collect(Collectors.toList());
        return articlesApiDTO;
    }

    @Override
    @Transactional
    public void addApiArticle(ArticleApiDTO apiArticle) {
        apiArticle.setUuid(UUID.randomUUID());
        LocalDateTime date = LocalDateTime.now();
        apiArticle.setCreatedAt(date);
        Article article = articleApiConverter.convertDTOToObject(apiArticle);
        articleRepository.persist(article);
    }

    @Override
    @Transactional
    public ArticleApiDTO findApiByUuid(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        Article article = articleRepository.findByUuid(uuid);
        if (Objects.nonNull(article)) {
            return articleApiConverter.convertObjectToDTO(article);
        } else {
            throw new ArticleNotFoundException(String.format("Article with uuid %s was not found", uuid));
        }
    }

    @Override
    @Transactional
    public ArticleApiDTO deleteArticleApiByUuid(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        Article article = articleRepository.findByUuid(uuid);
        if (Objects.nonNull(article)) {
            articleRepository.remove(article);
            return articleApiConverter.convertObjectToDTO(article);
        } else {
            throw new ArticleNotFoundException(String.format("Article with uuid %s was not found", uuid));
        }
    }
}
