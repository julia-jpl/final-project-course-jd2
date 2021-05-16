package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.generator.PasswordGenerator;
import com.gmail.portnova.julia.service.impl.UserAddServiceImpl;
import com.gmail.portnova.julia.service.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAddServiceTest {
    @Mock
    private PasswordGenerator passwordGenerator;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EmailService emailService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GeneralConverter<User, UserDTO> userConverter;
    @InjectMocks
    private UserAddServiceImpl userAddService;

    @Test
    void shouldChangePassword() {
        String uuidString = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(uuidString);
        User user = new User();
        user.setUuid(uuid);
        when(userRepository.findByUuid(uuid)).thenReturn(user);

        String newPassword = "1234";
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(uuid);
        userDTO.setPassword(encodedPassword);
        when(userConverter.convertObjectToDTO(user)).thenReturn(userDTO);

        UserDTO resultUser = userAddService.saveNewPasswordInDatabase(uuidString);
        assertEquals(encodedPassword, resultUser.getPassword());
    }
    @Test
    void shouldNotChangePasswordAndReturnException() {
        String uuidString = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(uuidString);
        when(userRepository.findByUuid(uuid)).thenReturn(null);
        assertThrows(UserNotFoundException.class, ()->userAddService.saveNewPasswordInDatabase(uuidString));
    }

}