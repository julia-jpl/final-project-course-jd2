package com.gmail.portnova.julia.repository;

import java.util.List;

public interface GenericRepository<I, T> {
    void persist(T entity);

    T findById(I id);

    List<T> findAll();

    void remove(T entity);
}
