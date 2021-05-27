package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.FeedbackDTO;
import com.gmail.portnova.julia.service.model.PageDTO;

import java.util.List;

public interface FeedbackService {

    List<FeedbackDTO> updateIsDisplayedStatus(List<String> idsAtPage, List<String> idsWithDisplayedTrue);

    FeedbackDTO deleteByUuid(String uuidString);

    PageDTO<FeedbackDTO> getAllFeedbackPage(int page, int maxResult);

    PageDTO<FeedbackDTO> getFeedbackByDisplayedTruePage(int pageNumber, int maxResult);

    List<FeedbackDTO> updateIsDisplayedStatus(List<String> idsAtPage);

    FeedbackDTO addFeedbackToDatabase(FeedbackDTO feedback);
}
