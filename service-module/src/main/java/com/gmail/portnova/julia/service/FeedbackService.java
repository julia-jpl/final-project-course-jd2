package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.FeedbackDTO;
import com.gmail.portnova.julia.service.model.PageDTO;

import java.util.List;

public interface FeedbackService {

    List<FeedbackDTO> updateIsDisplayedStatus(List<String> idsAtPage, List<String> idsWithDisplayedTrue);

    void deleteByUuid(String uuidString);

    PageDTO<FeedbackDTO> getAllFeedbackPage(Integer page, Integer maxResult);

    PageDTO<FeedbackDTO> getFeedbackByDisplayedTruePage(Integer pageNumber, Integer maxResult);

    List<FeedbackDTO> updateIsDisplayedStatus(List<String> idsAtPage);
}
