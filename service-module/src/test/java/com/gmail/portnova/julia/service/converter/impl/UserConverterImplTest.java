package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.RoleRepository;
import com.gmail.portnova.julia.repository.model.Role;
import com.gmail.portnova.julia.repository.model.RoleNameEnum;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.converter.impl.UserConverterImpl;
import com.gmail.portnova.julia.service.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserConverterImplTest {
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserConverterImpl userConverter;

    @Test
    void shouldConvertUserDTOtoUserAndReturnRightFirstName() {
        UserDTO userDTO = new UserDTO();
        String firstName = "test first name";
        userDTO.setFirstName(firstName);

        User user = userConverter.convertDTOToObject(userDTO);
        assertEquals(firstName, user.getFirstName());
    }

    @Test
    void shouldConvertUserDTOtoUserAndReturnRightEmail() {
        UserDTO userDTO = new UserDTO();
        String email = "aaaaa@gmail.com";
        userDTO.setEmail(email);

        User user = userConverter.convertDTOToObject(userDTO);
        assertEquals(email, user.getEmail());
    }

    @Test
    void shouldConvertUserDTOtoUserAndReturnRightRole() {
        UserDTO userDTO = new UserDTO();
        String roleName = RoleNameEnum.ADMINISTRATOR.name();
        userDTO.setRoleName(roleName);
        RoleNameEnum roleNameEnum = RoleNameEnum.valueOf(roleName);
        Role role = new Role();
        role.setRoleName(roleNameEnum);
        when(roleRepository.findByName(roleNameEnum)).thenReturn(role);

        User user = userConverter.convertDTOToObject(userDTO);
        assertEquals(role, user.getRole());
    }

    @Test
    void shouldConvertUserDTOtoUserAndReturnRightUuid() {
        UserDTO userDTO = new UserDTO();
        UUID uuid = UUID.randomUUID();
        userDTO.setUuid(uuid);

        User user = userConverter.convertDTOToObject(userDTO);
        assertEquals(uuid, user.getUuid());
    }

    @Test
    void shouldConvertUserDTOtoUserAndReturnRightLastName() {
        UserDTO userDTO = new UserDTO();
        String lastName = "last name";
        userDTO.setLastName(lastName);

        User user = userConverter.convertDTOToObject(userDTO);
        assertEquals(lastName, user.getLastName());
    }

    @Test
    void shouldConvertUserDTOtoUserAndReturnRightMiddleName() {
        UserDTO userDTO = new UserDTO();
        String middleName = "middle name";
        userDTO.setMiddleName(middleName);

        User user = userConverter.convertDTOToObject(userDTO);
        assertEquals(middleName, user.getMiddleName());
    }

    @Test
    void shouldConvertUserDTOtoUserAndReturnRightPassword() {
        UserDTO userDTO = new UserDTO();
        String password = "fhklllll;k;";
        userDTO.setPassword(password);

        User user = userConverter.convertDTOToObject(userDTO);
        assertEquals(password, user.getPassword());
    }
    @Test
    void shouldConvertUserDTOtoUserAndReturnNotNullObject() {
        UserDTO userDTO = new UserDTO();
        User user = userConverter.convertDTOToObject(userDTO);

        assertNotNull(user);
    }
    @Test
    void shouldConvertUserToUserDTOAndReturnNotNullObject() {
        User user = new User();
        UserDTO userDTO = userConverter.convertObjectToDTO(user);

        assertNotNull(userDTO);
    }

    @Test
    void shouldConvertUserToUserDTOAndReturnRightRole() {
        Role role = new Role();
        role.setRoleName(RoleNameEnum.ADMINISTRATOR);
        User user = new User();
        user.setRole(role);

        UserDTO userDTO = userConverter.convertObjectToDTO(user);
        assertEquals(RoleNameEnum.ADMINISTRATOR.name(), userDTO.getRoleName());
    }

    @Test
    void shouldConvertUserToUserDTOAndReturnRightEmail() {
        User user = new User();
        String email = "test@test.com";
        user.setEmail(email);

        UserDTO userDTO = userConverter.convertObjectToDTO(user);
        assertEquals(email, userDTO.getEmail());
    }
    @Test
    void shouldConvertUserToUserDTOAndReturnRightUuid() {
        User user = new User();
        UUID uuid = UUID.randomUUID();
        user.setUuid(uuid);

        UserDTO userDTO = userConverter.convertObjectToDTO(user);
        assertEquals(uuid, userDTO.getUuid());
    }
    @Test
    void shouldConvertUserToUserDTOAndReturnRightLastName() {
        User user = new User();
        String lastName = "test last name";
        user.setLastName(lastName);

        UserDTO userDTO = userConverter.convertObjectToDTO(user);
        assertEquals(lastName, userDTO.getLastName());
    }
    @Test
    void shouldConvertUserToUserDTOAndReturnRightFirstName() {
        User user = new User();
        String firstName = "test first name";
        user.setFirstName(firstName);

        UserDTO userDTO = userConverter.convertObjectToDTO(user);
        assertEquals(firstName, userDTO.getFirstName());
    }
    @Test
    void shouldConvertUserToUserDTOAndReturnRightMiddleName() {
        User user = new User();
        String middleName = "test middle name";
        user.setMiddleName(middleName);

        UserDTO userDTO = userConverter.convertObjectToDTO(user);
        assertEquals(middleName, userDTO.getMiddleName());
    }
    @Test
    void shouldConvertUserToUserDTOAndReturnRightPassword() {
        User user = new User();
        String password = "test password";
        user.setPassword(password);

        UserDTO userDTO = userConverter.convertObjectToDTO(user);
        assertEquals(password, userDTO.getPassword());
    }
}