package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.model.ArticleApiDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.gmail.portnova.julia.service.constant.ExceptionMessageConstant.ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE;

@Component
@RequiredArgsConstructor
public class ArticleApiConverterImpl implements GeneralConverter<Article, ArticleApiDTO> {
    private final UserRepository userRepository;

    @Override
    public ArticleApiDTO convertObjectToDTO(Article article) {
        if (Objects.nonNull(article)) {
            ArticleApiDTO articleApiDTO = new ArticleApiDTO();
            articleApiDTO.setId(article.getId());
            articleApiDTO.setUuid(article.getUuid());
            articleApiDTO.setTitle(article.getTitle());
            articleApiDTO.setContent(article.getContent());
            articleApiDTO.setCreatedAt(article.getCreatedAt());
            User user = article.getUser();
            if (Objects.nonNull(user)) {
                articleApiDTO.setUserUuid(user.getUuid());
                String userLastAndFirstName = String.join(" ", user.getLastName(), user.getFirstName());
                articleApiDTO.setAuthor(userLastAndFirstName);
            }
            else {
                articleApiDTO.setAuthor(article.getAuthor());
            }
            return articleApiDTO;
        } else {
            return null;
        }
    }

    @Override
    public Article convertDTOToObject(ArticleApiDTO articleApiDTO) {
        Article article = new Article();
        article.setUuid(articleApiDTO.getUuid());
        article.setTitle(articleApiDTO.getTitle());
        article.setContent(articleApiDTO.getContent());
        article.setCreatedAt(articleApiDTO.getCreatedAt());
        User user = userRepository.findByUuid(articleApiDTO.getUserUuid());
        if (Objects.nonNull(user)) {
            article.setUser(user);
            article.setAuthor(String.join(" ", user.getLastName(), user.getFirstName()));
            return article;
        } else {
            throw new UserNotFoundException(String.format(
                    ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE, User.class, articleApiDTO.getUserUuid()));
        }
    }
}
