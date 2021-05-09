package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.ArticleApiDTO;

import java.util.List;

public interface ArticleApiService {
    List<ArticleApiDTO> getAllArticles();

    void addApiArticle(ArticleApiDTO apiArticle);

    ArticleApiDTO findApiByUuid(String uuidString);

    ArticleApiDTO deleteArticleApiByUuid(String uuidString);
}
