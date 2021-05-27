package com.gmail.portnova.julia.web.controller.web;

import com.gmail.portnova.julia.service.CommentService;
import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.model.ArticleDTO;
import com.gmail.portnova.julia.service.model.CommentDTO;
import com.gmail.portnova.julia.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;


    @PostMapping("/customer/articles/comments/{uuid}")
    public String addComment(@Valid @ModelAttribute("userComment") CommentDTO comment,
                             BindingResult result,
                             @PathVariable("uuid") String articleUuid,
                             @ModelAttribute("article") ArticleDTO article) {
        UserDTO user = getCurrentUser();
        if (Objects.nonNull(user)) {
            if (result.hasErrors()) {
                return "article";
            } else {
                comment.setArticleUuid(articleUuid);
                UUID userUuid = user.getUuid();
                comment.setUserUuid(userUuid);
                commentService.addComment(comment);
                return "redirect:/articles/{uuid}";
            }
        } else {
            return "redirect:/403";
        }
    }

    @PostMapping("/sale/articles/{uuid}/comments/delete/{commentUuid}")
    public String deleteComment(@PathVariable String commentUuid,
                                @PathVariable String uuid) {
        commentService.deleteCommentByUuid(commentUuid);
        return "redirect:/sale/articles/{uuid}";
    }

    private UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        return userService.findUserByEmail(username);
    }
}
