package com.gmail.portnova.julia.repository;

import com.gmail.portnova.julia.repository.model.Feedback;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

public interface FeedbackRepository extends GenericRepository<Long, Feedback> {
    Long count();

    List<Feedback> findAllWithLimit(int startPosition, int maxResult);

    int setNotDisplayedStatusToAllFeedback();

    Feedback findByUuid(UUID uuid);

    List<Feedback> findAllWithStatusDisplayedForPagination(int startPosition, Integer maxResult);

    Long countFeedbackIdDisplayedTrue();

}
