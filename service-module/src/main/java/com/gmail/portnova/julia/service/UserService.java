package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO findUserByEmail(String username);

    List<UserDTO> findAllUsersExceptCurrent(String email, Integer page, Integer maxResult);

    Long countPages(Integer maxResult);

    void deleteByUUID(String id);
}
