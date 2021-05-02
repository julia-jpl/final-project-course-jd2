package com.gmail.portnova.julia.web.controller;

import com.gmail.portnova.julia.service.FeedbackService;
import com.gmail.portnova.julia.service.model.FeedbackDTO;
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
    private final Integer maxResult = 10;

    @PostMapping("/feedback/{page}")
    public String getStartPage(@PathVariable Integer page) {
        return "redirect:/feedback/1";
    }

    @GetMapping("/feedback/{page}")
    public String getFeedbackCommonPage(@PathVariable Integer page, Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (Objects.nonNull(userDetails)) {
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities) {
                String authorityName = grantedAuthority.getAuthority();
                if (authorityName.equals(RoleNameEnumDTO.ADMINISTRATOR.name())) {
                    Long numberOfPages = feedbackService.countPages(maxResult);
                    model.addAttribute("numberOfPages", numberOfPages);
                    model.addAttribute("currentPage", page);
                    List<FeedbackDTO> allFeedback = feedbackService.findAllFeedbackWithPagination(page, maxResult);
                    model.addAttribute("allFeedback", allFeedback);
                    return "all_feedback_administrator";
                } else {
                    Long numberOfPages = feedbackService.countIsDisplayedTrueFeedbackPages(maxResult);
                    model.addAttribute("numberOfPages", numberOfPages);
                    model.addAttribute("currentPage", page);
                    List<FeedbackDTO> feedbackToDisplay = feedbackService.findAllFeedbackWithStatusDisplayed(page, maxResult);
                    model.addAttribute("feedbackToDisplay", feedbackToDisplay);
                    return "all_feedback_common_page";
                }
            }
        }
        return "all_feedback_common_page";
    }

    @PostMapping("/users/feedback/update")
    public String updateFeedbackStatus(@RequestParam("feedbackIdsToUpdate") List<String> ids) {
        if (Objects.nonNull(ids)) {
            feedbackService.updateIsDisplayedStatus(ids);
            return "redirect:/feedback/1";
        } else
            return "all_feedback_administrator";
    }

    @PostMapping("/users/feedback/delete/{uuid}")
    public String deleteFeedback(@PathVariable String uuid) {
        feedbackService.deleteByUuid(uuid);
        return "redirect:/feedback/1";
    }
}


