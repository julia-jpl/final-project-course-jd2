package com.gmail.portnova.julia.repository.impl;

import com.gmail.portnova.julia.repository.GenericRepository;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.UUID;

@Log4j2
public class GenericRepositoryImpl<I, T> implements GenericRepository<I, T> {
    @PersistenceContext
    protected EntityManager entityManager;
    protected Class<T> entityClass;

    @SuppressWarnings({"unchecked"})
    public GenericRepositoryImpl() {
        ParameterizedType genericClass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericClass.getActualTypeArguments()[1];
    }

    @Override
    public void persist(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public T findById(I id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public List<T> findAll() {
        String queryString = "from " + entityClass.getName() + " c";
        Query query = entityManager.createQuery(queryString);
        return query.getResultList();
    }

    @Override
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public T findByUuid(UUID uuid) {
        String hql = "SELECT t FROM " + entityClass.getName() + " t WHERE t.uuid = :uuid";
        Query query = entityManager.createQuery(hql);
        query.setParameter("uuid", uuid);
        try {
            return (T) query.getSingleResult();
        } catch (NonUniqueResultException | NoResultException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Long count() {
        String hql = "SELECT COUNT (t.id) FROM " + entityClass.getName() + " t";
        Query query = entityManager.createQuery(hql);
        return (Long) query.getSingleResult();
    }
}
