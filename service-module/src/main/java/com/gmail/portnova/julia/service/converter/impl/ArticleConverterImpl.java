package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.model.ArticleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.gmail.portnova.julia.service.constant.ExceptionMessageConstant.USER_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.gmail.portnova.julia.service.constant.TimeFormatterConstant.DATE_TIME_FORMATTER;

@Component
@RequiredArgsConstructor
public class ArticleConverterImpl implements GeneralConverter<Article, ArticleDTO> {
    private final UserRepository userRepository;

    @Override
    public ArticleDTO convertObjectToDTO(Article article) {
        if (Objects.nonNull(article)) {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setId(article.getId());
            articleDTO.setUuid(article.getUuid());
            articleDTO.setCreatedAt(article.getCreatedAt().format(DATE_TIME_FORMATTER));
            if (Objects.nonNull(article.getUpdatedAt())) {
                String updatedAt = article.getUpdatedAt().format(DATE_TIME_FORMATTER);
                articleDTO.setUpdatedAt(updatedAt);
            } else {
                articleDTO.setUpdatedAt(null);
            }
            articleDTO.setTitle(article.getTitle());
            articleDTO.setContent(article.getContent());
            User user = article.getUser();
            if (Objects.nonNull(user)) {
                String userLastAndFirstName = String.join(" ", user.getLastName(), user.getFirstName());
                articleDTO.setUserLastAndFirstName(userLastAndFirstName);
            } else {
                articleDTO.setUserLastAndFirstName(article.getAuthor());
            }
            return articleDTO;
        } else {
            return null;
        }
    }

    @Override
    public Article convertDTOToObject(ArticleDTO articleDTO) {
        if (Objects.nonNull(articleDTO)) {
            Article article = new Article();
            article.setUuid(articleDTO.getUuid());
            article.setTitle(articleDTO.getTitle());
            article.setContent(articleDTO.getContent());
            if (Objects.nonNull(articleDTO.getCreatedAt())) {
                LocalDateTime createdAt = LocalDateTime.parse(articleDTO.getCreatedAt(), DATE_TIME_FORMATTER);
                article.setCreatedAt(createdAt);
            }
            User user = userRepository.findByUuid(articleDTO.getUserUuid());
            if (Objects.nonNull(user)) {
                article.setUser(user);
                article.setAuthor(String.join(" ", user.getLastName(), user.getFirstName()));
                return article;
            } else {
                throw new UserNotFoundException(String.format(USER_NOT_FOUND_EXCEPTION_MESSAGE, articleDTO.getUserUuid()));
            }
        } else {
            return null;
        }
    }
}
