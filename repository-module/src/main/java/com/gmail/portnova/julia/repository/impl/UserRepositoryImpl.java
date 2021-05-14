package com.gmail.portnova.julia.repository.impl;

import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.User;
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
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public User findByEmail(String username) {
        String hql = "FROM User as u WHERE u.email =:usernameParam";
        Query query = entityManager.createQuery(hql);
        query.setParameter("usernameParam", username);
        try {
            return (User) query.getSingleResult();
        } catch (NonUniqueResultException | NoResultException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<User> findAllExceptCurrent(String email, int startPosition, int maxResult) {
        String hql = "FROM User u WHERE NOT u.email = :email ORDER BY u.email";
        Query query = entityManager.createQuery(hql);
        query.setParameter("email", email);
        query.setFirstResult(startPosition);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }
}
