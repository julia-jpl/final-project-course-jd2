package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.Role;
import com.gmail.portnova.julia.repository.model.RoleNameEnum;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.RoleDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RoleConverterImpl implements GeneralConverter<Role, RoleDTO> {

    @Override
    public Role convertDTOToObject(RoleDTO object) {
        throw new UnsupportedOperationException("This method hasn't been implemented");
    }

    public RoleDTO convertObjectToDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        RoleNameEnum roleNameEnum = role.getRoleName();
        if (Objects.nonNull(roleNameEnum)) {
            roleDTO.setName(roleNameEnum.name());
        }
        return roleDTO;
    }
}
