package com.gmail.portnova.julia.repository;

import com.gmail.portnova.julia.repository.model.User;

import java.util.List;

public interface UserRepository extends GenericRepository<Long, User> {
    User findByEmail(String username);

    List<User> findAllExceptCurrent(String email, int startPosition, int maxResult);

    Long count();

    User findByUuid(String id);
}
