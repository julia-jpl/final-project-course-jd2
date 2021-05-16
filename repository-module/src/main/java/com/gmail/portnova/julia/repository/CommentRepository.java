package com.gmail.portnova.julia.repository;

import com.gmail.portnova.julia.repository.model.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends GenericRepository<Long, Comment> {
    List<Comment> findByArticleUuid(UUID articleUuid);
}
