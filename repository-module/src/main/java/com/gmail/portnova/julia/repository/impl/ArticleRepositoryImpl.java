package com.gmail.portnova.julia.repository.impl;

import com.gmail.portnova.julia.repository.ArticleRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.repository.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Log4j2
public class ArticleRepositoryImpl extends GenericRepositoryImpl<Long, Article> implements ArticleRepository {

    @Override
    public List<Article> findAllWithLimit(int startPosition, int maxResult) {
        String hql = "SELECT a.id, a.uuid, a.createdAt, a.updatedAt, a.author, a.title, SUBSTRING(a.content, 1, 200), a.user FROM Article a ORDER BY a.createdAt DESC";
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

    @Override
    public Long countArticleByUserUuid(UUID userUuid) {
        String hql = "SELECT COUNT (a.id) FROM Article a JOIN a.user u WHERE u.uuid = :id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", userUuid);
        return (Long) query.getSingleResult();
    }

    @Override
    public List<Article> findArticlesWithLimitByUserUuid(int startPosition, int maxResult, UUID userUuid) {
        String hql = "SELECT a.id, a.uuid, a.createdAt, a.updatedAt, a.author, a.title, SUBSTRING(a.content, 1, 200), a.user FROM Article a " +
                "JOIN a.user u WHERE u.uuid = :id ORDER BY a.createdAt DESC";
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(startPosition);
        query.setMaxResults(maxResult);
        query.setParameter("id", userUuid);
        List<Object> resultList = query.getResultList();
        List<Article> articles = new ArrayList<>();
        for (Object object : resultList) {
            Article article = getArticle((Object[]) object);
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
        LocalDateTime updatedAt = (LocalDateTime) objects[3];
        article.setUpdatedAt(updatedAt);
        String author = (String) objects[4];
        article.setAuthor(author);
        String title = (String) objects[5];
        article.setTitle(title);
        String summary = (String) objects[6];
        article.setContent(summary);
        User user = (User) objects[7];
        article.setUser(user);
        return article;
    }
}
