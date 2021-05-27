package com.gmail.portnova.julia.web.controller.web;

import com.gmail.portnova.julia.service.FeedbackService;
import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.model.FeedbackDTO;
import com.gmail.portnova.julia.service.model.PageDTO;
import com.gmail.portnova.julia.service.model.RoleNameEnumDTO;
import com.gmail.portnova.julia.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final UserService userService;

    @PostMapping("/feedback")
    public String getStartPage(@RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber) {
        return "redirect:/feedback";
    }

    @GetMapping("/feedback")
    public String getFeedbackPage(@RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                                  @RequestParam(name = "maxResult", defaultValue = "10") int maxResult,
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
                    String username = userDetails.getUsername();
                    UserDTO user = userService.findUserByEmail(username);
                    if (Objects.nonNull(user)) {
                        UUID uuid = user.getUuid();
                        model.addAttribute("userUuid", uuid);
                    }
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

    @GetMapping("/customer/feedback/add")
    public String getAddFeedbackPage(@ModelAttribute("newFeedback") FeedbackDTO feedback) {
        return "add_feedback";
    }

    @PostMapping("/customer/feedback/add")
    public String addFeedback(@Valid @ModelAttribute("newFeedback") FeedbackDTO feedback,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "add_feedback";
        } else {
            UserDTO currentUser = getCurrentUser();
            if (Objects.nonNull(currentUser)) {
                feedback.setUserUuid(currentUser.getUuid());
                feedbackService.addFeedbackToDatabase(feedback);
                return "redirect:/feedback";
            }
            return "redirect:/feedback";
        }
    }
    private UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        return userService.findUserByEmail(username);
    }
}


