package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.UserDTO;

public interface UserAddService {
    UserDTO sendEmail(UserDTO userDTO);

    UserDTO addUserToDatabase(UserDTO user);

    UserDTO changePassword(String id);
}
