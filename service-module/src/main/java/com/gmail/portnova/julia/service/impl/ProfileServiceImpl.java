package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.repository.model.UserDetail;
import com.gmail.portnova.julia.service.ProfileService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.model.ProfileUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static com.gmail.portnova.julia.service.constant.ExceptionMessageConstant.ENTITY_WITH_NAME_NOT_FOUND_EXCEPTION_MESSAGE;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;
    private final GeneralConverter<User, ProfileUserDTO> profileConverter;

    @Override
    @Transactional
    public ProfileUserDTO getUserProfile(UUID uuid) {
        User user = userRepository.findByUuid(uuid);
        if (Objects.nonNull(user)) {
            return profileConverter.convertObjectToDTO(user);
        } else {
            throw new UserNotFoundException(String.format("User with uuid %s was not found", uuid));
        }
    }

    @Transactional
    @Override
    public ProfileUserDTO changeUserAddress(String address, String id) {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findByUuid(uuid);
        if (Objects.nonNull(user)) {
            UserDetail userDetail = user.getUserDetail();
            if (Objects.nonNull(userDetail)) {
                userDetail.setAddress(address);
            } else {
                UserDetail newUserDetail = new UserDetail();
                newUserDetail.setAddress(address);
                user.setUserDetail(newUserDetail);
                newUserDetail.setUser(user);
            }
            return profileConverter.convertObjectToDTO(user);
        } else {
            throw new UserNotFoundException(String.format("User with uuid %s was not found", id));
        }
    }

    @Transactional
    @Override
    public ProfileUserDTO changeUserTelephone(String telephone, String id) {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findByUuid(uuid);
        if (Objects.nonNull(user)) {
            UserDetail userDetail = user.getUserDetail();
            if (Objects.nonNull(userDetail)) {
                userDetail.setTelephone(telephone);
            } else {
                UserDetail newUserDetail = new UserDetail();
                newUserDetail.setTelephone(telephone);
                user.setUserDetail(newUserDetail);
                newUserDetail.setUser(user);
            }
            return profileConverter.convertObjectToDTO(user);
        } else {
            throw new UserNotFoundException(String.format("User with uuid %s was not found", id));
        }
    }

    @Override
    @Transactional
    public ProfileUserDTO getUserProfileByEmail(String username) {
        User user = userRepository.findByEmail(username);
        if (Objects.nonNull(user)) {
            return profileConverter.convertObjectToDTO(user);
        } else throw new UserNotFoundException(String.format(ENTITY_WITH_NAME_NOT_FOUND_EXCEPTION_MESSAGE, User.class, username));
    }
}
