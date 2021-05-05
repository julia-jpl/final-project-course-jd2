package com.gmail.portnova.julia.web.controller;

import com.gmail.portnova.julia.service.FeedbackService;
import com.gmail.portnova.julia.service.model.FeedbackDTO;
import com.gmail.portnova.julia.service.model.PageDTO;
import com.gmail.portnova.julia.service.model.RoleNameEnumDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;

    @PostMapping("/feedback")
    public String getStartPage(@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber) {
        return "redirect:/feedback";
    }

    @GetMapping("/feedback")
    public String getFeedbackPage(@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
                                  @RequestParam(name = "maxResult", defaultValue = "10") Integer maxResult,
                                  Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (Objects.nonNull(userDetails)) {
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities) {
                String authorityName = grantedAuthority.getAuthority();
                if (authorityName.equals(RoleNameEnumDTO.ADMINISTRATOR.name())) {
                    PageDTO<FeedbackDTO> page = feedbackService.getAllFeedbackPage(pageNumber, maxResult);
                    model.addAttribute("page", page);
                    model.addAttribute("currentPage", pageNumber);
                    return "all_feedback_administrator";
                } else {
                    PageDTO<FeedbackDTO> commonPage = feedbackService.getFeedbackByDisplayedTruePage(pageNumber, maxResult);
                    model.addAttribute("commonPage", commonPage);
                    model.addAttribute("currentPage", pageNumber);
                    return "all_feedback_common_page";
                }
            }
        }
        return "all_feedback_common_page";
    }

    @PostMapping("/users/feedback/update")
    public String updateFeedbackStatus(@RequestParam(name = "feedbackIdsToUpdate", required = false) List<String> idsChosen,
                                       @RequestParam(name = "feedbackIds") List<String> idsAtPage) {
        if (Objects.nonNull(idsChosen)) {
            feedbackService.updateIsDisplayedStatus(idsAtPage, idsChosen);
            return "redirect:/feedback";
        } else
            feedbackService.updateIsDisplayedStatus(idsAtPage);
            return "redirect:/feedback";
    }

    @PostMapping("/users/feedback/delete/{uuid}")
    public String deleteFeedback(@PathVariable String uuid) {
        feedbackService.deleteByUuid(uuid);
        return "redirect:/feedback";
    }
}


