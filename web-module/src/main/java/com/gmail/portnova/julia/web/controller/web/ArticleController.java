package com.gmail.portnova.julia.web.controller.web;

import com.gmail.portnova.julia.service.ArticleService;
import com.gmail.portnova.julia.service.CommentService;
import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.model.ArticleDTO;
import com.gmail.portnova.julia.service.model.CommentDTO;
import com.gmail.portnova.julia.service.model.PageDTO;
import com.gmail.portnova.julia.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping("/articles")
    public String getArticlePage(@RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                                 @RequestParam(name = "maxResult", defaultValue = "10") int maxResult,
                                 Model model) {
        PageDTO<ArticleDTO> page = articleService.getArticlesPage(pageNumber, maxResult);
        model.addAttribute("page", page);
        model.addAttribute("currentPage", pageNumber);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        UserDTO user = userService.findUserByEmail(username);
        if (Objects.nonNull(user)) {
            UUID uuid = user.getUuid();
            model.addAttribute("userUuid", uuid);
        }
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
                             Authentication authentication) {
        if (result.hasErrors()) {
            return "redirect:/articles/{uuid}";
        } else {
            comment.setArticleUuid(articleUuid);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            UserDTO user = userService.findUserByEmail(username);
            if (Objects.nonNull(user)) {
                UUID userUuid = user.getUuid();
                comment.setUserUuid(userUuid);
                commentService.addComment(comment);
            }
            return "redirect:/articles/{uuid}";
        }
    }
}
