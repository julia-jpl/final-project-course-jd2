package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.RoleRepository;
import com.gmail.portnova.julia.repository.model.Role;
import com.gmail.portnova.julia.repository.model.RoleNameEnum;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.UserDTO;
import org.springframework.stereotype.Component;


@Component
public class UserConverterImpl implements GeneralConverter<User, UserDTO> {
    private final RoleRepository roleRepository;

    public UserConverterImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDTO convertObjectToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUuid(user.getUuid());
        userDTO.setLastName(user.getLastName());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRoleName(user.getRole().getRoleName().name());
        return userDTO;
    }

    @Override
    public User convertDTOToObject(UserDTO userDTO) {
        User user = new User();
        user.setUuid(userDTO.getUuid());
        user.setLastName(userDTO.getLastName());
        user.setFirstName(userDTO.getFirstName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        String roleString = userDTO.getRoleName();
        RoleNameEnum roleName = RoleNameEnum.valueOf(roleString);
        Role role = getRole(roleName);
        user.setRole(role);
        return user;
    }

    private Role getRole(RoleNameEnum roleName) {
        return roleRepository.findByName(roleName);
    }
}

