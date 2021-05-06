package com.gmail.portnova.julia.repository.impl;

import com.gmail.portnova.julia.repository.FeedbackRepository;
import com.gmail.portnova.julia.repository.model.Feedback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.UUID;

@Repository
public class FeedbackRepositoryImpl extends GenericRepositoryImpl<Long, Feedback> implements FeedbackRepository {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Long count() {
        String hql = "SELECT COUNT (f.id) FROM Feedback f";
        Query query = entityManager.createQuery(hql);
        return (Long) query.getSingleResult();
    }

    @Override
    public List<Feedback> findAllWithLimit(int startPosition, int maxResult) {
        String hql = "SELECT f FROM Feedback f";
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(startPosition);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public int setNotDisplayedStatusToAllFeedback() {
        String hql = "UPDATE Feedback f SET f.isDisplayed = :falseStatus WHERE f.isDisplayed = :trueStatus";
        Query query = entityManager.createQuery(hql);
        query.setParameter("falseStatus", false);
        query.setParameter("trueStatus", true);
        return query.executeUpdate();
    }

    @Override
    public Feedback findByUuid(UUID uuid) {
        String hql = "FROM Feedback f WHERE f.uuid = :id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", uuid);
        try {
            return (Feedback) query.getSingleResult();
        } catch (NonUniqueResultException | NoResultException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<Feedback> findAllWithStatusDisplayedForPagination(int startPosition, Integer maxResult) {
        String hql = "SELECT f FROM Feedback f WHERE f.isDisplayed = :trueStatus";
        Query query = entityManager.createQuery(hql);
        query.setParameter("trueStatus", true);
        query.setFirstResult(startPosition);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public Long countFeedbackIdDisplayedTrue() {
        String hql = "SELECT COUNT (f.id) FROM Feedback f WHERE f.isDisplayed = :trueStatus";
        Query query = entityManager.createQuery(hql);
        query.setParameter("trueStatus", true);
        return (Long) query.getSingleResult();
    }
}
