package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.ArticleRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.service.ArticleService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.ArticleNotFoundException;
import com.gmail.portnova.julia.service.model.ArticleDTO;
import com.gmail.portnova.julia.service.model.PageableArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public PageableArticle getArticlesPage(Integer pageNumber, Integer maxResult) {
        Long numberOfRows = articleRepository.count();
        PageableArticle pageDTO = new PageableArticle();
        pageDTO.setTotalPages(getNumberOfPages(numberOfRows, maxResult));
        int startPosition = getStartPosition(pageNumber, maxResult);
        List<Article> articles = articleRepository.findAllWithLimit(startPosition, maxResult);
        setPageDTOList(pageDTO, articles);
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


    private void setPageDTOList(PageableArticle pageDTO, List<Article> articles) {
        List<ArticleDTO> articleDTOS = new ArrayList<>();
        if (!articles.isEmpty()) {
            List<ArticleDTO> list = new ArrayList<>();
            for (Article article : articles) {
                ArticleDTO articleDTO = articleConverter.convertObjectToDTO(article);
                list.add(articleDTO);
            }
            articleDTOS = list;
        }
        pageDTO.getObjects().addAll(articleDTOS);
    }

    private int getStartPosition(Integer page, Integer maxResult) {
        return maxResult * (page - 1);
    }

    private Long getNumberOfPages(Long numberOfRows, Integer maxResult) {
        if (numberOfRows % maxResult == 0) {
            return numberOfRows / maxResult;
        } else {
            return numberOfRows / maxResult + 1;
        }
    }
}
