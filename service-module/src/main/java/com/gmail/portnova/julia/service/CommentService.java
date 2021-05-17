package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.CommentDTO;

import java.util.List;

public interface CommentService {
    List<CommentDTO> findByArticleUuid(String id);

    CommentDTO addComment(CommentDTO comment);

    CommentDTO deleteCommentByUuid(String uuid);
}
