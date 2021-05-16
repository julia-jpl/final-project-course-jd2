package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.ArticleApiDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ArticleApiConverterImpl implements GeneralConverter<Article, ArticleApiDTO> {
    private final UserRepository userRepository;

    @Override
    public ArticleApiDTO convertObjectToDTO(Article article) {
        ArticleApiDTO articleApiDTO = new ArticleApiDTO();
        articleApiDTO.setId(article.getId());
        articleApiDTO.setUuid(article.getUuid());
        articleApiDTO.setTitle(article.getTitle());
        articleApiDTO.setContent(article.getContent());
        articleApiDTO.setCreatedAt(article.getCreatedAt());
        User user = article.getUser();
        if (Objects.nonNull(user)) {
            articleApiDTO.setUserUuid(user.getUuid());
        }
        return articleApiDTO;
    }

    @Override
    public Article convertDTOToObject(ArticleApiDTO articleApiDTO) {
        Article article = new Article();
        article.setUuid(articleApiDTO.getUuid());
        article.setTitle(articleApiDTO.getTitle());
        article.setContent(articleApiDTO.getContent());
        article.setCreatedAt(articleApiDTO.getCreatedAt());
        User user = userRepository.findByUuid(articleApiDTO.getUserUuid());
        article.setUser(user);
        return article;
    }
}
