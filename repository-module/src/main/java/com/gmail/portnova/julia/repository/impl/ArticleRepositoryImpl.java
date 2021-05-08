package com.gmail.portnova.julia.repository.impl;

import com.gmail.portnova.julia.repository.ArticleRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.repository.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Log4j2
public class ArticleRepositoryImpl extends GenericRepositoryImpl<Long, Article> implements ArticleRepository {
    @Override
    public Long count() {
        String hql = "SELECT COUNT (a.id) FROM Article a";
        Query query = entityManager.createQuery(hql);
        return (Long) query.getSingleResult();
    }

    @Override
    public List<Article> findAllWithLimit(int startPosition, Integer maxResult) {
        String hql = "SELECT a.id, a.uuid, a.createdAt, a.title, SUBSTRING(a.content, 1, 200), a.user FROM Article a ORDER BY a.createdAt DESC";
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(startPosition);
        query.setMaxResults(maxResult);
        List<Object> resultList = query.getResultList();
        List<Article> articles = new ArrayList<>();
        for (Object o : resultList) {
            Article article = getArticle((Object[]) o);
            articles.add(article);
        }
        return articles;
    }

    protected Article getArticle(Object[] o) {
        Article article = new Article();
        Object[] objects = o;
        Long id = (Long) objects[0];
        article.setId(id);
        UUID uuid = (UUID) objects[1];
        article.setUuid(uuid);
        LocalDateTime date = (LocalDateTime) objects[2];
        article.setCreatedAt(date);
        String title = (String) objects[3];
        article.setTitle(title);
        String summary = (String) objects[4];
        article.setContent(summary);
        User user = (User) objects[5];
        article.setUser(user);
        return article;
    }
}
