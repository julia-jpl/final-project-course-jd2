package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.RoleRepository;
import com.gmail.portnova.julia.repository.model.Role;
import com.gmail.portnova.julia.repository.model.RoleNameEnum;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class UserConverterImpl implements GeneralConverter<User, UserDTO> {
    private final RoleRepository roleRepository;

    @Override
    public UserDTO convertObjectToDTO(User user) {
        if (Objects.nonNull(user)) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUuid(user.getUuid());
            userDTO.setLastName(user.getLastName());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setMiddleName(user.getMiddleName());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
            Role role = user.getRole();
            if (Objects.nonNull(role)) {
                String roleName = role.getRoleName().name();
                userDTO.setRoleName(roleName);
            }
            return userDTO;
        } else {
            return null;
        }
    }

    @Override
    public User convertDTOToObject(UserDTO userDTO) {
        if (Objects.nonNull(userDTO)) {
            User user = new User();
            user.setUuid(userDTO.getUuid());
            user.setLastName(userDTO.getLastName());
            user.setFirstName(userDTO.getFirstName());
            user.setMiddleName(userDTO.getMiddleName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setIsDeleted(userDTO.getDeleted());
            String roleString = userDTO.getRoleName();
            if (Objects.nonNull(roleString)) {
                RoleNameEnum roleName = RoleNameEnum.valueOf(roleString);
                Role role = getRole(roleName);
                user.setRole(role);
            }
            return user;
        } else {
            return null;
        }
    }

    private Role getRole(RoleNameEnum roleName) {
        return roleRepository.findByName(roleName);
    }
}

