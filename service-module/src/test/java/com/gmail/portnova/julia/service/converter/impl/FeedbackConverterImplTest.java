package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.Feedback;
import com.gmail.portnova.julia.service.model.FeedbackDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackConverterImplTest {
    private final FeedbackConverterImpl feedbackConverter = new FeedbackConverterImpl();
    private Feedback feedback;

    @BeforeEach
    void init() {
        feedback = new Feedback();
    }

    @Test
    void shouldConvertFeedbackToFeedbackDTOAndReturnNotNullResult() {
        FeedbackDTO feedbackDTO = feedbackConverter.convertObjectToDTO(feedback);

        assertNotNull(feedbackDTO);
    }

    @Test
    void shouldConvertFeedbackToFeedbackDTOAndReturnRightText() {
        String text = "test text";
        feedback.setText(text);
        FeedbackDTO feedbackDTO = feedbackConverter.convertObjectToDTO(feedback);

        assertEquals(text, feedbackDTO.getText());
    }

    @Test
    void shouldConvertFeedbackToFeedbackDTOAndReturnRightUuid() {
        UUID uuid = UUID.randomUUID();
        feedback.setUuid(uuid);
        FeedbackDTO feedbackDTO = feedbackConverter.convertObjectToDTO(feedback);

        assertEquals(uuid, feedbackDTO.getUuid());
    }

    @Test
    void shouldConvertFeedbackToFeedbackDTOAndReturnRightId() {
        Long id = 1l;
        feedback.setId(id);
        FeedbackDTO feedbackDTO = feedbackConverter.convertObjectToDTO(feedback);

        assertEquals(id, feedbackDTO.getId());
    }

    @Test
    void shouldConvertFeedbackToFeedbackDTOAndReturnRightCreatedAt() {
        LocalDateTime localDateTime = LocalDateTime.now();
        feedback.setCreatedAt(localDateTime);
        FeedbackDTO feedbackDTO = feedbackConverter.convertObjectToDTO(feedback);

        assertEquals(localDateTime, feedbackDTO.getCreatedAt());
    }

    @Test
    void shouldConvertFeedbackToFeedbackDTOAndReturnRightIsDisplayedStatus() {
        feedback.setIsDisplayed(true);
        FeedbackDTO feedbackDTO = feedbackConverter.convertObjectToDTO(feedback);

        assertTrue(feedbackDTO.getDisplayed());
    }
}