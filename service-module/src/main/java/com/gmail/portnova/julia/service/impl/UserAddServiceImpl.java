package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.EmailService;
import com.gmail.portnova.julia.service.UserAddService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.generator.PasswordGenerator;
import com.gmail.portnova.julia.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;

import static com.gmail.portnova.julia.service.constant.PasswordGenerationConstant.*;
@RequiredArgsConstructor
@Service
public class UserAddServiceImpl implements UserAddService {
    private final PasswordGenerator passwordGenerator;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final GeneralConverter<User, UserDTO> userConverter;

    @Override
    @Transactional
    public UserDTO addUser(UserDTO user) {
        String password = passwordGenerator.generatePassword(PASSWORD_LENGTH,
                FIRST_ASCII_CHARACTER_COD_USED_IN_PASSWORD,
                LAST_ASCII_CHARACTER_COD_USED_IN_PASSWORD);
        emailService.sendSimpleMessage(user.getEmail(), password);
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        user.setUuid(UUID.randomUUID());
        User newUser = userConverter.convertDTOToObject(user);
        userRepository.persist(newUser);
        return user;
    }

    @Override
    @Transactional
    public UserDTO changePassword(String id) {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findByUuid(uuid);
        if (Objects.nonNull(user)) {
            String password = passwordGenerator.generatePassword(PASSWORD_LENGTH,
                    FIRST_ASCII_CHARACTER_COD_USED_IN_PASSWORD,
                    LAST_ASCII_CHARACTER_COD_USED_IN_PASSWORD);
            emailService.sendSimpleMessage(user.getEmail(), password);
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
            return userConverter.convertObjectToDTO(user);
        } else {
            throw new UserNotFoundException(String.format("User with uuid %s was not found", id));
        }
    }
}
