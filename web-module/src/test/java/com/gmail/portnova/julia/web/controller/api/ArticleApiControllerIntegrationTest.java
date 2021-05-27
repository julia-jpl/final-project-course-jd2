package com.gmail.portnova.julia.web.controller.api;

import com.gmail.portnova.julia.service.impl.UserDetailsServiceImpl;
import com.gmail.portnova.julia.service.model.ArticleApiDTO;
import com.gmail.portnova.julia.web.controller.api.config.BaseIT;
import com.gmail.portnova.julia.web.controller.api.config.TestUserDetailsConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArticleApiControllerIntegrationTest extends BaseIT {

    @Sql({"/scripts/cleanFeedback.sql",
            "/scripts/cleanUserDetail.sql",
            "/scripts/cleanOpinion.sql",
            "/scripts/cleanArticle.sql",
            "/scripts/cleanItemUser.sql",
            "/scripts/cleanOrderDetail.sql",
            "/scripts/cleanOrder.sql",
            "/scripts/cleanUser.sql",
            "/scripts/cleanRole.sql",
            "/scripts/initRole.sql",
            "/scripts/initUser.sql",
            "/scripts/initArticle.sql"})
    @Test
    void shouldGetArticles() {
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity<List<ArticleApiDTO>> response = testRestTemplate
                .withBasicAuth("rest@myhost.com", "qwer")
                .exchange(
                        "/api/articles",
                        HttpMethod.GET,
                        request,
                        createParameterizedTypeReferenceListArticleApiDto()
                );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("title", response.getBody().get(0).getTitle());
    }

    protected ParameterizedTypeReference<List<ArticleApiDTO>> createParameterizedTypeReferenceListArticleApiDto() {
        return new ParameterizedTypeReference<>() {
        };
    }


    @Sql({"/scripts/cleanFeedback.sql",
            "/scripts/cleanUserDetail.sql",
            "/scripts/cleanOpinion.sql",
            "/scripts/cleanArticle.sql",
            "/scripts/cleanItemUser.sql",
            "/scripts/cleanOrderDetail.sql",
            "/scripts/cleanOrder.sql",
            "/scripts/cleanUser.sql",
            "/scripts/cleanRole.sql",
            "/scripts/initRole.sql",
            "/scripts/initUser.sql",
            "/scripts/initArticle.sql"})
    @Test
    void shouldAddArticle() {
        ArticleApiDTO articleApiDTO = new ArticleApiDTO();
        String title = "title";
        articleApiDTO.setTitle(title);
        String content = "content";
        articleApiDTO.setContent(content);

        HttpEntity<ArticleApiDTO> request = new HttpEntity<>(articleApiDTO, new HttpHeaders());

        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("rest@myhost.com", "qwer")
                .exchange(
                        "/api/articles",
                        HttpMethod.POST,
                        request,
                        String.class
                );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Sql({"/scripts/cleanFeedback.sql",
            "/scripts/cleanUserDetail.sql",
            "/scripts/cleanOpinion.sql",
            "/scripts/cleanArticle.sql",
            "/scripts/cleanItemUser.sql",
            "/scripts/cleanOrderDetail.sql",
            "/scripts/cleanOrder.sql",
            "/scripts/cleanUser.sql",
            "/scripts/cleanRole.sql",
            "/scripts/initRole.sql",
            "/scripts/initUser.sql",
            "/scripts/initArticle.sql"})
    @Test
    void shouldGetArticleByUuid() {

        String uuid = "3422b448-2900-4fd2-9183-8000de6f8343";
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity<ArticleApiDTO> response = testRestTemplate
                .withBasicAuth("rest@myhost.com", "qwer")
                .exchange(
                        "/api/articles/" + uuid,
                        HttpMethod.GET,
                        request,
                        createParameterizedTypeReferenceArticleApiDto()
                );


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(uuid, response.getBody().getUuid().toString());
    }

    @Sql({"/scripts/cleanFeedback.sql",
            "/scripts/cleanUserDetail.sql",
            "/scripts/cleanOpinion.sql",
            "/scripts/cleanArticle.sql",
            "/scripts/cleanItemUser.sql",
            "/scripts/cleanOrderDetail.sql",
            "/scripts/cleanOrder.sql",
            "/scripts/cleanUser.sql",
            "/scripts/initUser.sql",
            "/scripts/initArticle.sql"})
    @Test
    void shouldDeleteArticleByUuid() {

        String uuid = "3422b448-2900-4fd2-9183-8000de6f8343";
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("rest@myhost.com", "qwer")
                .exchange(
                        "/api/articles/" + uuid,
                        HttpMethod.DELETE,
                        request,
                        String.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    protected ParameterizedTypeReference<ArticleApiDTO> createParameterizedTypeReferenceArticleApiDto() {
        return new ParameterizedTypeReference<>() {
        };
    }

}