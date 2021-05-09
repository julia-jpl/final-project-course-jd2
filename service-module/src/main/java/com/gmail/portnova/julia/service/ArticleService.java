package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.ArticleDTO;
import com.gmail.portnova.julia.service.model.PageDTO;

public interface ArticleService {
    PageDTO<ArticleDTO> getArticlesPage(Integer pageNumber, Integer maxResult);

    ArticleDTO findByUuid(String id);
}
