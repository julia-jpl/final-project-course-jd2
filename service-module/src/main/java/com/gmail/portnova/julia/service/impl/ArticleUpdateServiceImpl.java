package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.ArticleRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.service.ArticleUpdateService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.ArticleNotFoundException;
import com.gmail.portnova.julia.service.model.ArticleDTO;
import com.gmail.portnova.julia.service.model.ArticleUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleUpdateServiceImpl implements ArticleUpdateService {
    private final ArticleRepository articleRepository;
    private final GeneralConverter<Article, ArticleDTO> articleConverter;

    @Override
    @Transactional
    public ArticleDTO updateArticle(ArticleUpdateDTO editedArticle, String uuid) {
        UUID id = UUID.fromString(uuid);
        Article article = articleRepository.findByUuid(id);
        if (Objects.nonNull(article)) {
            if (!editedArticle.getTitle().isEmpty()) {
                article.setTitle(editedArticle.getTitle());
            }
            if (!editedArticle.getContent().isEmpty()) {
                article.setContent(editedArticle.getContent());
            }
            LocalDateTime updatedAt = LocalDateTime.now();
            article.setUpdatedAt(updatedAt);
            return articleConverter.convertObjectToDTO(article);
        } else {
            throw new ArticleNotFoundException(String.format("Article with uuid %s was not found", uuid));
        }
    }
}
