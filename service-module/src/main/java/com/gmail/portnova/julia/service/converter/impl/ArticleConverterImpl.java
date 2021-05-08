package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.ArticleDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
public class ArticleConverterImpl implements GeneralConverter<Article, ArticleDTO> {
    @Override
    public ArticleDTO convertObjectToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setUuid(article.getUuid());
        articleDTO.setCreatedAt(article.getCreatedAt());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setSummary(article.getContent());
        User user = article.getUser();
        if (Objects.nonNull(user)) {
            String userLastAndFirstName = String.join(" ", user.getLastName(), user.getFirstName());
            articleDTO.setUserLastAndFirstName(userLastAndFirstName);
        }
        return articleDTO;
    }

    @Override
    public Article convertDTOToObject(ArticleDTO object) {
        throw new UnsupportedOperationException("This method hasn't been implemented");
    }
}
