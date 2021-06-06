package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.ArticleRepository;
import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.repository.model.Comment;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.ArticleNotFoundException;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.model.CommentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import static com.gmail.portnova.julia.service.constant.ExceptionMessageConstant.ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.gmail.portnova.julia.service.constant.TimeFormatterConstant.DATE_TIME_FORMATTER;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class CommentConverterImpl implements GeneralConverter<Comment, CommentDTO> {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    @Override
    public CommentDTO convertObjectToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setCommentUuid(comment.getUuid());
        commentDTO.setCreatedAt(comment.getCreatedAt().format(DATE_TIME_FORMATTER));
        commentDTO.setContent(comment.getContent());
        try {
            User user = comment.getUser();
            if (Objects.nonNull(user)) {
                String userLastAndFirstName = String.join(" ", user.getLastName(), user.getFirstName());
                commentDTO.setUserLastAndFirstName(userLastAndFirstName);
            }
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            commentDTO.setUserLastAndFirstName(comment.getAuthor());
        }
        return commentDTO;
    }

    @Override
    public Comment convertDTOToObject(CommentDTO commentDTO) {
        if (Objects.nonNull(commentDTO)) {
            Comment comment = new Comment();
            comment.setUuid(commentDTO.getCommentUuid());
            comment.setContent(commentDTO.getContent());
            LocalDateTime createdAt = LocalDateTime.parse(commentDTO.getCreatedAt(), DATE_TIME_FORMATTER);
            comment.setCreatedAt(createdAt);
            User user = userRepository.findByUuid(commentDTO.getUserUuid());
            if (Objects.nonNull(user)) {
                comment.setUser(user);
                String author = String.join(" ", user.getLastName(), user.getFirstName());
                comment.setAuthor(author);
                String articleUuidString = commentDTO.getArticleUuid();
                if (Objects.nonNull(articleUuidString)) {
                    UUID articleUuid = UUID.fromString(articleUuidString);
                    Article article = articleRepository.findByUuid(articleUuid);
                    if (Objects.nonNull(article)) {
                        comment.setArticle(article);
                        return comment;
                    } else {
                        throw new ArticleNotFoundException(String.format(
                                ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE, Article.class, articleUuidString));
                    }
                } else {
                    throw new ArticleNotFoundException(String.format(
                            ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE, Article.class, commentDTO.getArticleUuid()));
                }
            } else {
                throw new UserNotFoundException(String.format(
                        ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE, User.class, commentDTO.getUserUuid()));
            }
        } else {
            return null;
        }
    }
}
