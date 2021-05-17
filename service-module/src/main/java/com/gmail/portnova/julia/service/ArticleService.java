package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.ArticleDTO;
import com.gmail.portnova.julia.service.model.PageDTO;

import java.util.UUID;

public interface ArticleService {
    PageDTO<ArticleDTO> getArticlesPage(int pageNumber, int maxResult);

    ArticleDTO findByUuid(String id);

    ArticleDTO deleteArticleByUuid(String uuid);

    PageDTO<ArticleDTO> getSaleUserArticlesPage(int pageNumber, int maxResult, UUID uuid);

    ArticleDTO addArticle(ArticleDTO article);
}
