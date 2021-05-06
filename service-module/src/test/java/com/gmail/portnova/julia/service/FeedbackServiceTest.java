package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.repository.FeedbackRepository;
import com.gmail.portnova.julia.repository.model.Feedback;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.FeedbackNotFoundException;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.impl.FeedbackServiceImpl;
import com.gmail.portnova.julia.service.model.FeedbackDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {
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
}