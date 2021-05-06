package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.FeedbackRepository;
import com.gmail.portnova.julia.repository.model.Feedback;
import com.gmail.portnova.julia.service.FeedbackService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.FeedbackDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final GeneralConverter<Feedback, FeedbackDTO> feedbackConverter;

    @Override
    @Transactional
    public List<FeedbackDTO> findAllFeedbackWithPagination(Integer page, Integer maxResult) {
        int startPosition = maxResult * (page - 1);
        List<Feedback> allFeedBack = feedbackRepository.findAllWithLimit(startPosition, maxResult);
        if (!allFeedBack.isEmpty()) {
            return allFeedBack.stream()
                    .map(feedbackConverter::convertObjectToDTO)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public Long countPages(Integer maxResult) {
        Long numberOfRows = feedbackRepository.count();
        return (numberOfRows / maxResult) + 1;
    }

    @Override
    @Transactional
    public List<FeedbackDTO> updateIsDisplayedStatus(List<String> ids) {
        feedbackRepository.setNotDisplayedStatusToAllFeedback();
        List<FeedbackDTO> displayedFeedback = new ArrayList<>();
        for (String id : ids) {
            UUID uuid = UUID.fromString(id);
            Feedback feedback = feedbackRepository.findByUuid(uuid);
            if (Objects.nonNull(feedback)) {
                feedback.setIsDisplayed(true);
                FeedbackDTO feedbackDTO = feedbackConverter.convertObjectToDTO(feedback);
                displayedFeedback.add(feedbackDTO);
            }
        }
        return displayedFeedback;
    }

    @Override
    @Transactional
    public void deleteByUuid(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        Feedback feedback = feedbackRepository.findByUuid(uuid);
        if (Objects.nonNull(feedback)) {
            feedbackRepository.remove(feedback);
        }
    }

    @Override
    public List<FeedbackDTO> findAllFeedbackWithStatusDisplayed(Integer page, Integer maxResult) {
        int startPosition = maxResult * (page - 1);
        List<Feedback> feedbackToDisplay = feedbackRepository.findAllWithStatusDisplayedForPagination(startPosition, maxResult);
        if (!feedbackToDisplay.isEmpty()) {
            return feedbackToDisplay.stream()
                    .map(feedbackConverter::convertObjectToDTO)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Long countIsDisplayedTrueFeedbackPages(Integer maxResult) {
        Long numberOfRows = feedbackRepository.countFeedbackIdDisplayedTrue();
        return (numberOfRows / maxResult) + 1;
    }
}
