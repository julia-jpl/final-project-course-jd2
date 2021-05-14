package com.gmail.portnova.julia.web.controller.api;

import com.gmail.portnova.julia.service.model.ArticleApiDTO;
import com.gmail.portnova.julia.web.controller.api.config.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
class ArticleApiControllerIntegrationTest extends BaseIT {
    @Sql({"/scripts/cleanOpinion.sql", "/scripts/cleanArticle.sql", "/scripts/initArticle.sql"})
    @Test
    void shouldGetArticles() {
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity<List<ArticleApiDTO>> response = testRestTemplate.exchange(
                "/api/articles",
                HttpMethod.GET,
                request,
                createParameterizedTypeReference()
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("title", response.getBody().get(0).getTitle());
    }

    protected ParameterizedTypeReference<List<ArticleApiDTO>> createParameterizedTypeReference() {
        return new ParameterizedTypeReference<>() {
        };
    }
}