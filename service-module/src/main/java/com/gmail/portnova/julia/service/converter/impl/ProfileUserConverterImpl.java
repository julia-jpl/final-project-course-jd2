package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.repository.model.UserDetail;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.ProfileUserDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProfileUserConverterImpl implements GeneralConverter<User, ProfileUserDTO> {
    @Override
    public ProfileUserDTO convertObjectToDTO(User user) {
        if (Objects.nonNull(user)) {
            ProfileUserDTO profileDTO = new ProfileUserDTO();
            profileDTO.setId(user.getId());
            profileDTO.setUuid(user.getUuid());
            profileDTO.setLastName(user.getLastName());
            profileDTO.setFirstName(user.getFirstName());
            UserDetail userDetail = user.getUserDetail();
            if (Objects.nonNull(userDetail)) {
                profileDTO.setAddress(userDetail.getAddress());
                profileDTO.setTelephone(userDetail.getTelephone());
            }
            return profileDTO;
        }
        return null;
    }

    @Override
    public User convertDTOToObject(ProfileUserDTO object) {
        throw new UnsupportedOperationException("This method hasn't been implemented");
    }
}
