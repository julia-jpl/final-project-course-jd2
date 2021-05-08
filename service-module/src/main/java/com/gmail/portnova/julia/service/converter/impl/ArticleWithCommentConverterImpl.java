package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.repository.model.Comment;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.ArticleWithCommentsDTO;
import com.gmail.portnova.julia.service.model.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ArticleWithCommentConverterImpl implements GeneralConverter<Article, ArticleWithCommentsDTO> {

    private final GeneralConverter<Comment, CommentDTO> commentConverter;

    @Override
    public ArticleWithCommentsDTO convertObjectToDTO(Article article) {
        ArticleWithCommentsDTO articleWithComments = new ArticleWithCommentsDTO();
        articleWithComments.setId(article.getId());
        articleWithComments.setUuid(article.getUuid());
        articleWithComments.setTitle(article.getTitle());
        articleWithComments.setCreatedAt(article.getCreatedAt());
        articleWithComments.setContent(article.getContent());
        User user = article.getUser();
        if (Objects.nonNull(user)) {
            String userLastAndFirstName = String.join(" ", user.getLastName(), user.getFirstName());
            articleWithComments.setUserLastAndFirstName(userLastAndFirstName);
        }
        List<Comment> comments = article.getComments();
        List<CommentDTO> commentDTOS = comments.stream()
                .map(commentConverter::convertObjectToDTO)
                .collect(Collectors.toList());
        articleWithComments.setComments(commentDTOS);
        return articleWithComments;
    }

    @Override
    public Article convertDTOToObject(ArticleWithCommentsDTO object) {
        throw new UnsupportedOperationException("This method hasn't been implemented");
    }
}
