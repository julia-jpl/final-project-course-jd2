package com.gmail.portnova.julia.repository;

import com.gmail.portnova.julia.repository.model.Article;

import java.util.List;

public interface ArticleRepository extends GenericRepository<Long, Article> {
    List<Article> findAllWithLimit(int startPosition, int maxResult);
}
