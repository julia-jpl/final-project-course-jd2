package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.repository.RoleRepository;
import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.Role;
import com.gmail.portnova.julia.repository.model.RoleNameEnum;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.exception.UserRoleNotFoundException;
import com.gmail.portnova.julia.service.impl.UserServiceImpl;
import com.gmail.portnova.julia.service.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private GeneralConverter<User, UserDTO> userConverter;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldFindUserByEmail() {
        String email = "test@test.com";
        User user = new User();
        user.setLastName(email);

        when(userRepository.findByEmail(email)).thenReturn(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        when(userConverter.convertObjectToDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.findUserByEmail(email);
        assertEquals(email, result.getEmail());
    }

    @Test
    void shouldFindUserByEmailWithNegativeResult() {
        String email = "test@test.com";
        when(userRepository.findByEmail(email)).thenReturn(null);

        UserDTO result = userService.findUserByEmail(email);
        assertNull(result);
    }

    @Test
    void shouldFindByUUID() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        User user = new User();
        user.setUuid(uuid);
        when(userRepository.findByUuid(uuid)).thenReturn(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(uuid);
        when(userConverter.convertObjectToDTO(user)).thenReturn(userDTO);

        UserDTO resultUser = userService.findByUuid(id);
        assertEquals(id, resultUser.getUuid().toString());
    }

    @Test
    void shouldNegativeFindByUUID() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);

        when(userRepository.findByUuid(uuid)).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userService.findByUuid(id));
    }

    @Test
    void shouldNotDeleteByUUID() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);

        when(userRepository.findByUuid(uuid)).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userService.deleteByUUID(id));
    }
    @Test
    void shouldDeleteByUUID() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        User user = new User();
        user.setUuid(uuid);
        when(userRepository.findByUuid(uuid)).thenReturn(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(uuid);
        when(userConverter.convertObjectToDTO(user)).thenReturn(userDTO);

        UserDTO resultUser = userService.deleteByUUID(id);
        assertEquals(id, resultUser.getUuid().toString());
    }
    @Test
    void shouldChangeUserRole() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        User user = new User();
        user.setUuid(uuid);
        when(userRepository.findByUuid(uuid)).thenReturn(user);
        String roleName = "ADMINISTRATOR";
        RoleNameEnum roleNameEnum = RoleNameEnum.valueOf(roleName);
        Role role = new Role();
        role.setRoleName(roleNameEnum);
        when(roleRepository.findByName(roleNameEnum)).thenReturn(role);
        user.setRole(role);
        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(uuid);
        userDTO.setRoleName(roleName);
        when(userConverter.convertObjectToDTO(user)).thenReturn(userDTO);

        UserDTO resultUser = userService.changeUserRole(id, roleName);
        assertEquals(uuid, resultUser.getUuid());
        assertEquals(roleName, resultUser.getRoleName());
    }

    @Test
    void shouldNotChangeUserRoleAndReturnUserRoleNotFoundException() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        User user = new User();
        user.setUuid(uuid);
        when(userRepository.findByUuid(uuid)).thenReturn(user);
        String roleName = "ADMINISTRATOR";
        RoleNameEnum roleNameEnum = RoleNameEnum.valueOf(roleName);

        when(roleRepository.findByName(roleNameEnum)).thenReturn(null);
        assertThrows(UserRoleNotFoundException.class, () -> userService.changeUserRole(id, roleName));
    }
    @Test
    void shouldNotChangeUserRoleAndReturnUserNotFoundException() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        User user = new User();
        user.setUuid(uuid);
        when(userRepository.findByUuid(uuid)).thenReturn(null);
        String roleName = "ADMINISTRATOR";

        assertThrows(UserNotFoundException.class, () -> userService.changeUserRole(id, roleName));
    }
}