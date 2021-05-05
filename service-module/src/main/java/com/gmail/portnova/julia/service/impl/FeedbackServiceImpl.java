package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.FeedbackRepository;
import com.gmail.portnova.julia.repository.model.Feedback;
import com.gmail.portnova.julia.service.FeedbackService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.FeedbackDTO;
import com.gmail.portnova.julia.service.model.PageDTO;
import com.gmail.portnova.julia.service.model.PageableFeedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final GeneralConverter<Feedback, FeedbackDTO> feedbackConverter;

    @Override
    @Transactional
    public List<FeedbackDTO> updateIsDisplayedStatus(List<String> idsAtPage, List<String> idsWithDisplayedTrue) {
        List<Feedback> feedbackForDisplayedTrue = getFeedbackListById(idsWithDisplayedTrue);
        List<String> idsWithDisplayedFalse = idsAtPage.stream()
                .filter(id -> !idsWithDisplayedTrue.contains(id))
                .collect(Collectors.toList());
        List<Feedback> feedbackForDisplayedFalse = getFeedbackListById(idsWithDisplayedFalse);
        for (Feedback feedback : feedbackForDisplayedTrue) {
            if (!feedback.getIsDisplayed()) {
                feedback.setIsDisplayed(true);
            }
        }
        for (Feedback feedback : feedbackForDisplayedFalse) {
            if (feedback.getIsDisplayed()) {
                feedback.setIsDisplayed(false);
            }
        }
        List<Feedback> feedbackAtPage = new ArrayList<>();
        feedbackAtPage.addAll(feedbackForDisplayedFalse);
        feedbackAtPage.addAll(feedbackForDisplayedTrue);
        return feedbackAtPage.stream()
                .map(feedbackConverter::convertObjectToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<FeedbackDTO> updateIsDisplayedStatus(List<String> idsAtPage) {
        List<Feedback> feedbackForDisplayedFalse = getFeedbackListById(idsAtPage);
        for (Feedback feedback : feedbackForDisplayedFalse) {
            if (feedback.getIsDisplayed()) {
                feedback.setIsDisplayed(false);
            }
        }
        return feedbackForDisplayedFalse.stream()
                .map(feedbackConverter::convertObjectToDTO)
                .collect(Collectors.toList());
    }

    private List<Feedback> getFeedbackListById(List<String> ids) {
        List<Feedback> feedbacks = new ArrayList<>();
        for (String id : ids) {
            UUID uuid = UUID.fromString(id);
            Feedback feedback = feedbackRepository.findByUuid(uuid);
            feedbacks.add(feedback);
        }
        return feedbacks;
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
    @Transactional
    public PageDTO<FeedbackDTO> getAllFeedbackPage(Integer page, Integer maxResult) {
        Long numberOfRows = feedbackRepository.count();
        PageableFeedback pageDTO = new PageableFeedback();
        pageDTO.setTotalPages((numberOfRows / maxResult) + 1);
        int startPosition = getStartPosition(page, maxResult);
        List<Feedback> allFeedBack = feedbackRepository.findAllWithLimit(startPosition, maxResult);
        setPageDTOList(pageDTO, allFeedBack);
        return pageDTO;
    }

    @Override
    @Transactional
    public PageDTO<FeedbackDTO> getFeedbackByDisplayedTruePage(Integer pageNumber, Integer maxResult) {
        Long numberOfRows = feedbackRepository.countFeedbackIdDisplayedTrue();
        PageableFeedback pageDTO = new PageableFeedback();
        pageDTO.setTotalPages(getNumberOfPages(numberOfRows, maxResult));
        int startPosition = getStartPosition(pageNumber, maxResult);
        List<Feedback> allFeedbackByDisplayedTrue = feedbackRepository.findAllWithStatusDisplayedForPagination(startPosition, maxResult);
        setPageDTOList(pageDTO, allFeedbackByDisplayedTrue);
        return pageDTO;
    }

    private void setPageDTOList(PageableFeedback pageDTO, List<Feedback> feedback) {
        List<FeedbackDTO> feedbackDTO = new ArrayList<>();
        if (!feedback.isEmpty()) {
            feedbackDTO.addAll(feedback.stream()
                    .map(feedbackConverter::convertObjectToDTO)
                    .collect(Collectors.toList()));
        }
        pageDTO.getObjects().addAll(feedbackDTO);
    }

    private int getStartPosition(Integer page, Integer maxResult) {
        return maxResult * (page - 1);
    }

    private Long getNumberOfPages(Long numberOfRows, Integer maxResult) {
        if (numberOfRows % maxResult == 0) {
            return numberOfRows / maxResult;
        } else {
            return numberOfRows / maxResult + 1;
        }
    }
}
