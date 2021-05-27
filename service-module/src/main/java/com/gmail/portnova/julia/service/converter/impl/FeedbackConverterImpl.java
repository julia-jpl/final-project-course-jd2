package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.Feedback;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.model.FeedbackDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import static com.gmail.portnova.julia.service.constant.ExceptionMessageConstant.ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FeedbackConverterImpl implements GeneralConverter<Feedback, FeedbackDTO> {
    private final UserRepository userRepository;

    @Override
    public FeedbackDTO convertObjectToDTO(Feedback feedback) {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setUuid(feedback.getUuid());
        feedbackDTO.setText(feedback.getText());
        feedbackDTO.setCreatedAt(feedback.getCreatedAt());
        feedbackDTO.setDisplayed(feedback.getIsDisplayed());
        User user = feedback.getUser();
        if (Objects.nonNull(user)) {
            String userFullName = String.join(" ", user.getLastName(), user.getFirstName(), user.getMiddleName());
            feedbackDTO.setUserFullName(userFullName);
            feedbackDTO.setUserUuid(user.getUuid());
        } else {
            feedbackDTO.setUserFullName(feedback.getAuthor());
        }
        return feedbackDTO;
    }

    @Override
    public Feedback convertDTOToObject(FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        feedback.setUuid(feedbackDTO.getUuid());
        feedback.setIsDisplayed(feedbackDTO.getDisplayed());
        feedback.setCreatedAt(feedbackDTO.getCreatedAt());
        feedback.setText(feedbackDTO.getText());
        User user = userRepository.findByUuid(feedbackDTO.getUserUuid());
        if (Objects.nonNull(user)) {
            feedback.setUser(user);
            String author = String.join(" ", user.getLastName(), user.getFirstName(), user.getMiddleName());
            feedback.setAuthor(author);
            return feedback;
        } else {
            throw new UserNotFoundException(
                    String.format(ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE,
                            User.class, feedbackDTO.getUserUuid()));
        }
    }
}
