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
    public UserDTO sendEmail(UserDTO user) {
        emailService.sendSimpleMessage(user.getEmail(), user.getPassword());
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return user;
    }

    @Transactional
    @Override
    public UserDTO addUserToDatabase(UserDTO user) {
        String password = passwordGenerator.generatePassword(PASSWORD_LENGTH,
                FIRST_ASCII_CHARACTER_COD_USED_IN_PASSWORD,
                LAST_ASCII_CHARACTER_COD_USED_IN_PASSWORD);
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        user.setUuid(UUID.randomUUID());
        User newUser = userConverter.convertDTOToObject(user);
        userRepository.persist(newUser);
        UserDTO savedUser = userConverter.convertObjectToDTO(newUser);
        savedUser.setPassword(password);
        return savedUser;
    }

    @Transactional
    @Override
    public UserDTO saveNewPasswordInDatabase(String id) {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findByUuid(uuid);
        if (Objects.nonNull(user)) {
            String password = passwordGenerator.generatePassword(PASSWORD_LENGTH,
                    FIRST_ASCII_CHARACTER_COD_USED_IN_PASSWORD,
                    LAST_ASCII_CHARACTER_COD_USED_IN_PASSWORD);
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
            UserDTO userWithNewPassword = userConverter.convertObjectToDTO(user);
            userWithNewPassword.setPassword(password);
            return userWithNewPassword;
        } else {
            throw new UserNotFoundException(String.format("User with uuid %s was not found", id));
        }
    }
}
