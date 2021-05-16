package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.ArticleRepository;
import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.repository.model.Comment;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CommentConverterImpl implements GeneralConverter<Comment, CommentDTO> {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    @Override
    public CommentDTO convertObjectToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setCommentUuid(comment.getUuid());
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setContent(comment.getContent());
        User user = comment.getUser();
        if (Objects.nonNull(user)) {
            String userLastAndFirstName = String.join(" ", user.getLastName(), user.getFirstName());
            commentDTO.setUserLastAndFirstName(userLastAndFirstName);
        }
        return commentDTO;
    }

    @Override
    public Comment convertDTOToObject(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setUuid(commentDTO.getCommentUuid());
        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(commentDTO.getCreatedAt());
        User user = userRepository.findByUuid(commentDTO.getUserUuid());
        if (Objects.nonNull(user)) {
            comment.setUser(user);
            String articleUuidString = commentDTO.getArticleUuid();
            UUID articleUuid = UUID.fromString(articleUuidString);
            Article article = articleRepository.findByUuid(articleUuid);
            if (Objects.nonNull(article)) {
                comment.setArticle(article);
                return comment;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
