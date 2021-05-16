package com.gmail.portnova.julia.web.controller.web;

import com.gmail.portnova.julia.service.ArticleService;
import com.gmail.portnova.julia.service.CommentService;
import com.gmail.portnova.julia.service.model.ArticleDTO;
import com.gmail.portnova.julia.service.model.CommentDTO;
import com.gmail.portnova.julia.service.model.PageDTO;
import com.gmail.portnova.julia.service.model.UserLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final CommentService commentService;

    @GetMapping("/articles")
    public String getArticlePage(@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
                                 @RequestParam(name = "maxResult", defaultValue = "10") Integer maxResult,
                                 Model model) {
        PageDTO<ArticleDTO> page = articleService.getArticlesPage(pageNumber, maxResult);
        model.addAttribute("page", page);
        model.addAttribute("currentPage", pageNumber);
        return "all_articles";
    }

    @GetMapping("/articles/{uuid}")
    public String getArticle(@PathVariable("uuid") String id, Model model,
                             @ModelAttribute("userComment") CommentDTO comment) {
        ArticleDTO article = articleService.findByUuid(id);
        model.addAttribute("article", article);
        List<CommentDTO> comments = commentService.findByArticleUuid(id);
        model.addAttribute("comments", comments);
        return "article";
    }

    @PostMapping("/articles/comments/{uuid}")
    public String addComment(@ModelAttribute("userComment") CommentDTO comment,
                             BindingResult result,
                             @PathVariable("uuid") String articleUuid,
                             Authentication auth) {
        if (result.hasErrors()) {
            return "redirect:/articles/{uuid}";
        } else {
            comment.setArticleUuid(articleUuid);
            UserLogin userLogin = (UserLogin) auth.getPrincipal();
            UUID userUuid = userLogin.getUuid();
            comment.setUserUuid(userUuid);
            commentService.addComment(comment);
            return "redirect:/articles/{uuid}";
        }
    }
}
