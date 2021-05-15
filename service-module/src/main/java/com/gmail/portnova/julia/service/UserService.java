package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.PageDTO;
import com.gmail.portnova.julia.service.model.ProfileUserDTO;
import com.gmail.portnova.julia.service.model.UserDTO;

public interface UserService {
    UserDTO findUserByEmail(String username);

    UserDTO deleteByUUID(String id);

    UserDTO findByUuid(String id);

    UserDTO changeUserRole(String id, String newRole);

    PageDTO<UserDTO> getUsersPage(String email, int pageNumber, int maxResult);

    UserDTO changeUserLastname(String lastName, String id);

    UserDTO changeUserFirstName(String firstName, String id);
}
