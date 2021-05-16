package com.gmail.portnova.julia.repository;

import java.util.List;
import java.util.UUID;

public interface GenericRepository<I, T> {
    void persist(T entity);

    T findById(I id);

    List<T> findAll();

    void remove(T entity);

    T findByUuid(UUID uuid);

    Long count();
}
