package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.ProfileUserDTO;

public interface ProfileService {
    ProfileUserDTO getUserProfile(String id);

    void changeUserProfile(String name, String uuid);
}
