package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.Feedback;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.FeedbackDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FeedbackConverterImpl implements GeneralConverter<Feedback, FeedbackDTO> {
    @Override
    public FeedbackDTO convertObjectToDTO(Feedback feedback) {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setId(feedback.getId());
        feedbackDTO.setUuid(feedback.getUuid());
        feedbackDTO.setText(feedback.getText());
        feedbackDTO.setCreatedAt(feedback.getCreatedAt());
        feedbackDTO.setDisplayed(feedback.getIsDisplayed());
        User user = feedback.getUser();
        if (Objects.nonNull(user)) {
            String userFullName = String.join(" ", user.getLastName(), user.getFirstName(), user.getMiddleName());
            feedbackDTO.setUserFullName(userFullName);
            feedbackDTO.setUserUuid(user.getUuid());
        }
        return feedbackDTO;
    }

    @Override
    public Feedback convertDTOToObject(FeedbackDTO feedbackDTO) {
        throw new UnsupportedOperationException("This method hasn't been implemented");
    }
}
