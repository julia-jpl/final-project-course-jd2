package com.gmail.portnova.julia.web.controller.api;

import com.gmail.portnova.julia.service.ArticleApiService;
import com.gmail.portnova.julia.service.exception.ArticleNotFoundException;
import com.gmail.portnova.julia.service.model.ArticleApiDTO;
import com.gmail.portnova.julia.service.model.UserLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleApiController {
    private final ArticleApiService articleApiService;

    @GetMapping
    public List<ArticleApiDTO> getArticles() {
        return articleApiService.getAllArticles();
    }

    @PostMapping
    public ResponseEntity<Void> addArticle(@Valid @RequestBody ArticleApiDTO article,
                                           BindingResult result,
                                           Authentication auth) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserLogin userLogin = (UserLogin) auth.getPrincipal();
        UUID userUuid = userLogin.getUuid();
        article.setUserUuid(userUuid);
        articleApiService.addApiArticle(article);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ArticleApiDTO getArticleByUuid(@PathVariable("id") String uuidString) {
        return articleApiService.findApiByUuid(uuidString);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticleByUuid(@PathVariable("id") String uuidString) {
        try {
            articleApiService.deleteArticleApiByUuid(uuidString);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
