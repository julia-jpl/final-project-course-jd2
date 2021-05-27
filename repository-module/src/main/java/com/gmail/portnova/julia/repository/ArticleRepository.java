package com.gmail.portnova.julia.repository;

import com.gmail.portnova.julia.repository.model.Article;

import java.util.List;
import java.util.UUID;

public interface ArticleRepository extends GenericRepository<Long, Article> {
    List<Article> findAllWithLimit(int startPosition, int maxResult);

    Long countArticleByUserUuid(UUID userUuid);

    List<Article> findArticlesWithLimitByUserUuid(int startPosition, int maxResult, UUID userUuid);
}
