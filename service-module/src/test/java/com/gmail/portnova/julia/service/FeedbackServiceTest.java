package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.repository.FeedbackRepository;
import com.gmail.portnova.julia.repository.model.Feedback;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.impl.FeedbackServiceImpl;
import com.gmail.portnova.julia.service.model.FeedbackDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {
    @Mock
    private FeedbackRepository feedbackRepository;
    @Mock
    private GeneralConverter<Feedback, FeedbackDTO> feedbackConverter;
    @InjectMocks
    private FeedbackServiceImpl feedbackService;


}