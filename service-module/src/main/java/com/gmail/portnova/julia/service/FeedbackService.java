package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.FeedbackDTO;

import java.util.List;

public interface FeedbackService {
    List<FeedbackDTO> findAllFeedbackWithPagination(Integer page, Integer maxResult);

    Long countPages(Integer maxResult);

    List<FeedbackDTO> updateIsDisplayedStatus(List<String> ids);

    void deleteByUuid(String uuidString);

    List<FeedbackDTO> findAllFeedbackWithStatusDisplayed(Integer page, Integer maxResult);

    Long countIsDisplayedTrueFeedbackPages(Integer maxResult);

}
