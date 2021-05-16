package com.gmail.portnova.julia.repository.impl;

import com.gmail.portnova.julia.repository.CommentRepository;
import com.gmail.portnova.julia.repository.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

@Repository
public class CommentRepositoryImpl extends GenericRepositoryImpl<Long, Comment> implements CommentRepository {
    @Override
    public List<Comment> findByArticleUuid(UUID articleUuid) {
        String hql = "SELECT c FROM Comment c JOIN c.article a WHERE a.uuid = :id ORDER BY c.createdAt desc";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", articleUuid);
        return query.getResultList();
    }
}
