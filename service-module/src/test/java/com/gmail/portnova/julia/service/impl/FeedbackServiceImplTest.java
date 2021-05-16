package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.FeedbackRepository;
import com.gmail.portnova.julia.repository.model.Feedback;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.FeedbackNotFoundException;
import com.gmail.portnova.julia.service.model.FeedbackDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceImplTest {
    @Mock
    private FeedbackRepository feedbackRepository;
    @Mock
    private GeneralConverter<Feedback, FeedbackDTO> feedbackConverter;
    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    @Test
    void shouldDeleteByUuid() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        Feedback feedback = new Feedback();
        feedback.setUuid(uuid);
        when(feedbackRepository.findByUuid(uuid)).thenReturn(feedback);

        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setUuid(uuid);
        when(feedbackConverter.convertObjectToDTO(feedback)).thenReturn(feedbackDTO);

        FeedbackDTO resultFeedback = feedbackService.deleteByUuid(id);
        assertEquals(id, resultFeedback.getUuid().toString());
    }

    @Test
    void shouldNotDeleteByUUID() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);

        when(feedbackRepository.findByUuid(uuid)).thenReturn(null);
        assertThrows(FeedbackNotFoundException.class, () -> feedbackService.deleteByUuid(id));
    }

    @Test
    void shouldGetFeedbackListById() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuidFromString = UUID.fromString(id);
        List<String> ids = Collections.singletonList(id);
        List<Feedback> feedbacks = new ArrayList<>(0);
        for (String idString : ids) {
            UUID uuid = UUID.fromString(idString);
            Feedback feedback = new Feedback();
            feedback.setUuid(uuid);
            when(feedbackRepository.findByUuid(uuid)).thenReturn(feedback);
            feedbacks.add(feedback);
        }

        List<Feedback> resultFeedbackList = feedbackService.getFeedbackListById(ids);

        assertEquals(uuidFromString, resultFeedbackList.get(0).getUuid());
    }

    @Test
    void shouldGetEmptyListById() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        List<String> ids = Collections.singletonList(id);
        List<Feedback> feedbacks = new ArrayList<>(0);
        for (String idString : ids) {
            UUID uuid = UUID.fromString(idString);
            when(feedbackRepository.findByUuid(uuid)).thenReturn(null);
        }

        List<Feedback> resultFeedbackList = feedbackService.getFeedbackListById(ids);

        assertEquals(Collections.emptyList(), resultFeedbackList);
    }
}