package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.RoleRepository;
import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.Role;
import com.gmail.portnova.julia.repository.model.RoleNameEnum;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.repository.model.UserDetail;
import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.exception.UserRoleNotFoundException;
import com.gmail.portnova.julia.service.model.PageDTO;
import com.gmail.portnova.julia.service.model.PageableUser;
import com.gmail.portnova.julia.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GeneralConverter<User, UserDTO> userConverter;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDTO findUserByEmail(String username) {
        User user = userRepository.findByEmail(username);
        if (Objects.nonNull(user)) {
            return userConverter.convertObjectToDTO(user);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public UserDTO deleteByUUID(String id) {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findByUuid(uuid);
        if (Objects.nonNull(user)) {
            userRepository.remove(user);
            return userConverter.convertObjectToDTO(user);
        } else {
            throw new UserNotFoundException(String.format("User with uuid %s was not found", id));
        }
    }

    @Override
    @Transactional
    public UserDTO findByUuid(String id) {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findByUuid(uuid);
        if (Objects.nonNull(user)) {
            return userConverter.convertObjectToDTO(user);
        } else {
            throw new UserNotFoundException(String.format("User with uuid %s was not found", id));
        }
    }

    @Override
    @Transactional
    public UserDTO changeUserRole(String id, String newRole) {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findByUuid(uuid);
        if (Objects.nonNull(user)) {
            RoleNameEnum roleName = RoleNameEnum.valueOf(newRole);
            Role role = roleRepository.findByName(roleName);
            if (Objects.nonNull(role)) {
                user.setRole(role);
                return userConverter.convertObjectToDTO(user);
            } else {
                throw new UserRoleNotFoundException(String.format("Role with name %s was not found", newRole));
            }
        } else {
            throw new UserNotFoundException(String.format("User with uuid %s was not found", id));
        }
    }

    @Transactional
    @Override
    public PageDTO<UserDTO> getUsersPage(String email, Integer pageNumber, Integer maxResult) {
        Long numberOfRows = userRepository.count();
        Long numberOfRowsExceptCurrentUser = numberOfRows - 1;
        PageableUser page = new PageableUser();
        page.setTotalPages(getNumberOfPages(numberOfRowsExceptCurrentUser, maxResult));
        int startPosition = getStartPosition(pageNumber, maxResult);
        List<User> users = userRepository.findAllExceptCurrent(email, startPosition, maxResult);
        setPageDTOList(page, users);
        return page;
    }

    @Transactional
    @Override
    public UserDTO changeUserLastname(String lastName, String id) {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findByUuid(uuid);
        if (Objects.nonNull(user)) {
            user.setLastName(lastName);
            return userConverter.convertObjectToDTO(user);
        } else {
            throw new UserNotFoundException(String.format("User with uuid %s was not found", id));
        }
    }

    @Transactional
    @Override
    public UserDTO changeUserFirstName(String firstName, String id) {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findByUuid(uuid);
        if (Objects.nonNull(user)) {
            user.setFirstName(firstName);
            return userConverter.convertObjectToDTO(user);
        } else {
            throw new UserNotFoundException(String.format("User with uuid %s was not found", id));
        }
    }

    @Transactional
    @Override
    public void changeUserAddress(String address, String id) {
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
        } else {
            throw new UserNotFoundException(String.format("User with uuid %s was not found", id));
        }
    }

    @Transactional
    @Override
    public void changeUserTelephone(String telephone, String id) {
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
        } else {
            throw new UserNotFoundException(String.format("User with uuid %s was not found", id));
        }
    }

    private int getStartPosition(Integer page, Integer maxResult) {
        return maxResult * (page - 1);
    }

    private void setPageDTOList(PageableUser page, List<User> users) {
        List<UserDTO> userDTOS = new ArrayList<>();
        if (!users.isEmpty()) {
            userDTOS.addAll(users.stream()
                    .map(userConverter::convertObjectToDTO)
                    .collect(Collectors.toList()));
        }
        page.getObjects().addAll(userDTOS);
    }

    private Long getNumberOfPages(Long numberOfRows, Integer maxResult) {
        if (numberOfRows % maxResult == 0) {
            return numberOfRows / maxResult;
        } else {
            return numberOfRows / maxResult + 1;
        }
    }
}
