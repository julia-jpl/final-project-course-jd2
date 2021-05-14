package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.ProfileUserDTO;

import java.util.UUID;

public interface ProfileService {
    ProfileUserDTO getUserProfile(UUID uuid);
}
