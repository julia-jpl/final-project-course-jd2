package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.ProfileUserDTO;

import java.util.UUID;

public interface ProfileService {
    ProfileUserDTO getUserProfile(UUID uuid);

    ProfileUserDTO changeUserAddress(String address, String id);

    void changeProfileTelephone(String telephone, String id);

    ProfileUserDTO getUserProfileByEmail(String username);

    ProfileUserDTO changeUserLastname(String lastName, String id);

    void changeProfileFirstName(String firstName, String id);
}
