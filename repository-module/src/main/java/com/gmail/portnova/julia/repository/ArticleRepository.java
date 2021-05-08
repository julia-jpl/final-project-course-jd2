package com.gmail.portnova.julia.repository;

import com.gmail.portnova.julia.repository.model.Article;

import java.util.List;
import java.util.UUID;

public interface ArticleRepository extends GenericRepository<Long, Article> {
    Long count();

    List<Article> findAllWithLimit(int startPosition, Integer maxResult);

}
