package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.RoleRepository;
import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.Role;
import com.gmail.portnova.julia.repository.model.RoleNameEnum;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.repository.model.UserDetail;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.exception.UserRoleNotFoundException;
import com.gmail.portnova.julia.service.model.PageDTO;
import com.gmail.portnova.julia.service.model.PageableUser;
import com.gmail.portnova.julia.service.model.ProfileUserDTO;
import com.gmail.portnova.julia.service.model.UserDTO;
import com.gmail.portnova.julia.service.util.PageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.timeout;
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

        assertThrows(UserNotFoundException.class, () -> userService.findUserByEmail(email));
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

    @Test
    void shouldGetUsersPage() {
        int pageNumber = 1;
        int maxResult = 10;
        Long numberOfRows = 1L;
        when(userRepository.count()).thenReturn(numberOfRows);

        Long numberOfPages = PageUtil.getNumberOfPages(numberOfRows, maxResult);

        PageableUser pageDTO = new PageableUser();
        pageDTO.setTotalPages(numberOfPages);

        int startPosition = PageUtil.getStartPosition(pageNumber, maxResult);
        String currentUsername = "test@test.com";
        User user = new User();
        List<User> users = Collections.singletonList(user);
        when(userRepository.findAllExceptCurrent(currentUsername, startPosition, maxResult)).thenReturn(users);

        UserDTO userDTO = new UserDTO();
        when(userConverter.convertObjectToDTO(user)).thenReturn(userDTO);
        List<UserDTO> userDTOS = userService.getPageDTOList(users);

        pageDTO.getObjects().addAll(userDTOS);

        PageDTO<UserDTO> resultPage = userService.getUsersPage(currentUsername, pageNumber, maxResult);

        assertEquals(numberOfPages, resultPage.getTotalPages());
        assertEquals(userDTO, resultPage.getObjects().get(0));
    }

    @Test
    void shouldGetUsersDTOList() {
        User user = new User();
        List<User> users = Collections.singletonList(user);

        UserDTO userDTO = new UserDTO();
        when(userConverter.convertObjectToDTO(user)).thenReturn(userDTO);

        List<UserDTO> resultUserDTOS = userService.getPageDTOList(users);
        assertEquals(userDTO, resultUserDTOS.get(0));
    }

    @Test
    void shouldGetUserDTOEmptyList() {
        List<User> users = Collections.emptyList();

        List<UserDTO> resultUserDTOS = userService.getPageDTOList(users);
        assertEquals(Collections.emptyList(), resultUserDTOS);
    }

    @Test
    void shouldChangeUserLastname() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        User user = new User();
        user.setUuid(uuid);
        when(userRepository.findByUuid(uuid)).thenReturn(user);

        String newName = "name";
        user.setLastName(newName);

        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(uuid);
        userDTO.setLastName(newName);
        when(userConverter.convertObjectToDTO(user)).thenReturn(userDTO);

        UserDTO resultUser = userService.changeUserLastname(newName, id);
        assertEquals(newName, resultUser.getLastName());
    }

    @Test
    void shouldNotChangeUserLastname() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        String newName = "name";

        when(userRepository.findByUuid(uuid)).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userService.changeUserLastname(newName, id));
    }

    @Test
    void shouldChangeUserFirstname() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        User user = new User();
        user.setUuid(uuid);
        when(userRepository.findByUuid(uuid)).thenReturn(user);

        String newName = "name";
        user.setFirstName(newName);

        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(uuid);
        userDTO.setFirstName(newName);
        when(userConverter.convertObjectToDTO(user)).thenReturn(userDTO);

        UserDTO resultUser = userService.changeUserFirstName(newName, id);
        assertEquals(newName, resultUser.getFirstName());
    }

    @Test
    void shouldNotChangeUserFirstname() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        String newName = "name";

        when(userRepository.findByUuid(uuid)).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userService.changeUserFirstName(newName, id));
    }
}