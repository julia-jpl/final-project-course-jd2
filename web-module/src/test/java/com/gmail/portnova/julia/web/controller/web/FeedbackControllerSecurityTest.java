package com.gmail.portnova.julia.web.controller.web;

import com.gmail.portnova.julia.service.FeedbackService;
import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.model.FeedbackDTO;
import com.gmail.portnova.julia.service.model.PageDTO;
import com.gmail.portnova.julia.service.model.PageableFeedback;
import com.gmail.portnova.julia.service.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FeedbackController.class)
class FeedbackControllerSecurityTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private FeedbackService feedbackService;
    @MockBean
    private UserService userService;


    @WithMockUser(authorities = {"CUSTOMER_USER"})
    @Test
    void whenValidInputAndReturn200status() throws Exception {
        int pageNumber = 1;
        int maxResult = 10;
        Long totalPages = 1L;
        List<FeedbackDTO> feedbackDTOS = Collections.emptyList();
        PageDTO<FeedbackDTO> page = new PageableFeedback();
        page.setTotalPages(totalPages);
        page.setObjects(feedbackDTOS);

        UUID uuid = UUID.randomUUID();
        String email = "test";
        UserDTO user = new UserDTO();
        user.setUuid(uuid);
        user.setEmail(email);

        when(feedbackService.getAllFeedbackPage(pageNumber, maxResult)).thenReturn(page);
        when(feedbackService.getFeedbackByDisplayedTruePage(pageNumber, maxResult)).thenReturn(page);
        when(userService.findUserByEmail(email)).thenReturn(user);

        mockMvc.perform(
                get("/feedback")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }


}