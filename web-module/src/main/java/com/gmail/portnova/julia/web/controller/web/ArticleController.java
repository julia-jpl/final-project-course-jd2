package com.gmail.portnova.julia.web.controller.web;

import com.gmail.portnova.julia.service.ArticleService;
import com.gmail.portnova.julia.service.model.ArticleDTO;
import com.gmail.portnova.julia.service.model.ArticleWithCommentsDTO;
import com.gmail.portnova.julia.service.model.PageDTO;
import com.gmail.portnova.julia.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/articles")
    public String getArticlePage(@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
                                 @RequestParam(name = "maxResult", defaultValue = "10") Integer maxResult,
                                 Model model) {
        PageDTO<ArticleDTO> page = articleService.getArticlesPage(pageNumber, maxResult);
        model.addAttribute("page", page);
        model.addAttribute("currentPage", pageNumber);
        return "all_articles";
    }
    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable("id") String id, Model model) {
        ArticleWithCommentsDTO article = articleService.findByUuid(id);
        model.addAttribute("article", article);
        return "article";
    }
}
