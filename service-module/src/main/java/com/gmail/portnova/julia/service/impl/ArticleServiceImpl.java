package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.ArticleRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.service.ArticleService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.ArticleNotFoundException;
import com.gmail.portnova.julia.service.model.ArticleDTO;
import com.gmail.portnova.julia.service.model.PageDTO;
import com.gmail.portnova.julia.service.model.PageableArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static com.gmail.portnova.julia.service.util.PageUtil.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final GeneralConverter<Article, ArticleDTO> articleConverter;

    @Override
    @Transactional
    public PageableArticle getArticlesPage(int pageNumber, int maxResult) {
        Long numberOfRows = articleRepository.count();
        PageableArticle pageDTO = new PageableArticle();
        Long numberOfPages = getNumberOfPages(numberOfRows, maxResult);
        pageDTO.setTotalPages(numberOfPages);
        int startPosition = getStartPosition(pageNumber, maxResult);
        List<Article> articles = articleRepository.findAllWithLimit(startPosition, maxResult);
        List<ArticleDTO> articleDTOS = getArticleDTOList(articles);
        pageDTO.getObjects().addAll(articleDTOS);
        return pageDTO;
    }

    @Override
    @Transactional
    public ArticleDTO findByUuid(String id) {
        UUID uuid = UUID.fromString(id);
        Article article = articleRepository.findByUuid(uuid);
        if (Objects.nonNull(article)) {
            ArticleDTO articleDTO = articleConverter.convertObjectToDTO(article);
            return articleDTO;
        } else {
            throw new ArticleNotFoundException(String.format("Article with uuid %s was not found", id));
        }
    }

    @Override
    @Transactional
    public ArticleDTO deleteArticleByUuid(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        Article article = articleRepository.findByUuid(uuid);
        if (Objects.nonNull(article)) {
            articleRepository.remove(article);
            return articleConverter.convertObjectToDTO(article);
        } else {
            throw new ArticleNotFoundException(String.format("Article with uuid %s was not found", uuidString));
        }
    }

    @Override
    @Transactional
    public PageDTO<ArticleDTO> getSaleUserArticlesPage(int pageNumber, int maxResult, UUID userUuid) {
        Long numberOfRows = articleRepository.countArticleByUserUuid(userUuid);
        PageableArticle pageDTO = new PageableArticle();
        Long numberOfPages = getNumberOfPages(numberOfRows, maxResult);
        pageDTO.setTotalPages(numberOfPages);
        int startPosition = getStartPosition(pageNumber, maxResult);
        List<Article> articles = articleRepository.findArticlesWithLimitByUserUuid(startPosition, maxResult, userUuid);
        List<ArticleDTO> articleDTOS = getArticleDTOList(articles);
        pageDTO.getObjects().addAll(articleDTOS);
        return pageDTO;
    }

    @Override
    @Transactional
    public ArticleDTO addArticle(ArticleDTO newArticle) {
        UUID articleUuid = UUID.randomUUID();
        newArticle.setUuid(articleUuid);
        Article article = articleConverter.convertDTOToObject(newArticle);
        articleRepository.persist(article);
        return newArticle;
    }


    protected List<ArticleDTO> getArticleDTOList(List<Article> articles) {
        List<ArticleDTO> articleDTOS = new ArrayList<>();
        if (!articles.isEmpty()) {
            List<ArticleDTO> list = new ArrayList<>();
            for (Article article : articles) {
                ArticleDTO articleDTO = articleConverter.convertObjectToDTO(article);
                list.add(articleDTO);
            }
            articleDTOS = list;
        }
        return articleDTOS;
    }
}
