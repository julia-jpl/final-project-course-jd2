package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.ArticleDTO;
import com.gmail.portnova.julia.service.model.ArticleUpdateDTO;

public interface ArticleUpdateService {
    ArticleDTO updateArticle(ArticleUpdateDTO editedArticle, String uuid);
}
