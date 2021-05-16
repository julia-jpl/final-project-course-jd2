package com.gmail.portnova.julia.repository;

import com.gmail.portnova.julia.repository.model.Feedback;

import java.util.List;

public interface FeedbackRepository extends GenericRepository<Long, Feedback> {
    List<Feedback> findAllWithLimit(int startPosition, int maxResult);

    List<Feedback> findAllWithStatusDisplayedForPagination(int startPosition, int maxResult);

    Long countFeedbackIdDisplayedTrue();

}
