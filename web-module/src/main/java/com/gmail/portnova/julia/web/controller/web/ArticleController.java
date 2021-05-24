package com.gmail.portnova.julia.web.controller.web;

import com.gmail.portnova.julia.service.ArticleService;
import com.gmail.portnova.julia.service.ArticleUpdateService;
import com.gmail.portnova.julia.service.CommentService;
import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;
    private final ArticleUpdateService articleUpdateService;

    @GetMapping("/articles")
    public String getArticlePage(@RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                                 @RequestParam(name = "maxResult", defaultValue = "10") int maxResult,
                                 Model model) {
        PageDTO<ArticleDTO> page = articleService.getArticlesPage(pageNumber, maxResult);
        model.addAttribute("page", page);
        model.addAttribute("currentPage", pageNumber);
        return "all_articles";
    }

    @GetMapping("/sale/articles")
    public String getSaleUserArticles(@RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                                      @RequestParam(name = "maxResult", defaultValue = "10") int maxResult,
                                      Model model) {
        UserDTO user = getCurrentUser();
        if (Objects.nonNull(user)) {
            PageDTO<ArticleDTO> page = articleService.getSaleUserArticlesPage(pageNumber, maxResult, user.getUuid());
            model.addAttribute("page", page);
            model.addAttribute("currentPage", pageNumber);
            return "sale_articles";
        } else {
            return "redirect:/403";
        }
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

    @GetMapping("/sale/articles/{uuid}")
    public String getSaleArticle(@PathVariable("uuid") String id, Model model,
                                 @ModelAttribute("editedArticle") ArticleUpdateDTO editedArticle) {
        ArticleDTO article = articleService.findByUuid(id);
        model.addAttribute("article", article);
        List<CommentDTO> comments = commentService.findByArticleUuid(id);
        model.addAttribute("comments", comments);
        return "article_sale_user_page";
    }

    @PostMapping("/sale/articles/update/{uuid}")
    public String updateArticle(@PathVariable("uuid") String id,
                                @Valid @ModelAttribute("editedArticle") ArticleUpdateDTO editedArticle,
                                @ModelAttribute("article") ArticleDTO article,
                                BindingResult result) {
        if (result.hasErrors() ||
                (editedArticle.getContent().isEmpty() &&
                        editedArticle.getTitle().isEmpty())) {
            return "redirect:/sale/articles/{uuid}";
        } else {
            articleUpdateService.updateArticle(editedArticle, id);
            return "redirect:/sale/articles/{uuid}";
        }
    }

    @PostMapping("/sale/articles/delete/{uuid}")
    public String deleteArticle(@PathVariable String uuid) {
        articleService.deleteArticleByUuid(uuid);
        return "redirect:/sale/articles";
    }

    @GetMapping("/sale/articles/add")
    public String getAddArticlePage(@ModelAttribute("newArticle") ArticleDTO article) {
        return "add_article";
    }

    @PostMapping("/sale/articles/add")
    public String addArticle(@Valid @ModelAttribute("newArticle") ArticleDTO article,
                             BindingResult result) {
        UserDTO user = getCurrentUser();
        if (Objects.nonNull(user)) {
            if (result.hasErrors()) {
                return "add_article";
            }
            article.setUserUuid(user.getUuid());
            articleService.addArticle(article);
            return "redirect:/sale/articles";
        } else {
            return "redirect:/403";
        }
    }

    private UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        return userService.findUserByEmail(username);
    }
}
