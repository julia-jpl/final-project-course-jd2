package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.Feedback;
import com.gmail.portnova.julia.service.model.FeedbackDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.gmail.portnova.julia.service.constant.TimeFormatterConstant.DATE_TIME_FORMATTER;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FeedbackConverterImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private FeedbackConverterImpl feedbackConverter;
    private Feedback feedback;

    @BeforeEach
    void init() {
        feedback = new Feedback();
    }

    @Test
    void shouldConvertFeedbackToFeedbackDTOAndReturnNotNullResult() {
        LocalDateTime localDateTime = LocalDateTime.now();
        feedback.setCreatedAt(localDateTime);

        FeedbackDTO feedbackDTO = feedbackConverter.convertObjectToDTO(feedback);
        assertNotNull(feedbackDTO);
    }

    @Test
    void shouldConvertFeedbackToFeedbackDTOAndReturnRightText() {
        String text = "test text";
        feedback.setText(text);
        LocalDateTime localDateTime = LocalDateTime.now();
        feedback.setCreatedAt(localDateTime);
        FeedbackDTO feedbackDTO = feedbackConverter.convertObjectToDTO(feedback);

        assertEquals(text, feedbackDTO.getText());
    }

    @Test
    void shouldConvertFeedbackToFeedbackDTOAndReturnRightUuid() {
        UUID uuid = UUID.randomUUID();
        feedback.setUuid(uuid);
        LocalDateTime localDateTime = LocalDateTime.now();
        feedback.setCreatedAt(localDateTime);
        FeedbackDTO feedbackDTO = feedbackConverter.convertObjectToDTO(feedback);

        assertEquals(uuid, feedbackDTO.getUuid());
    }

    @Test
    void shouldConvertFeedbackToFeedbackDTOAndReturnRightCreatedAt() {
        LocalDateTime localDateTime = LocalDateTime.now();
        feedback.setCreatedAt(localDateTime);
        FeedbackDTO feedbackDTO = feedbackConverter.convertObjectToDTO(feedback);

        assertEquals(localDateTime.format(DATE_TIME_FORMATTER), feedbackDTO.getCreatedAt());
    }

    @Test
    void shouldConvertFeedbackToFeedbackDTOAndReturnRightIsDisplayedStatus() {
        feedback.setIsDisplayed(true);
        LocalDateTime localDateTime = LocalDateTime.now();
        feedback.setCreatedAt(localDateTime);
        FeedbackDTO feedbackDTO = feedbackConverter.convertObjectToDTO(feedback);

        assertTrue(feedbackDTO.getDisplayed());
    }
}