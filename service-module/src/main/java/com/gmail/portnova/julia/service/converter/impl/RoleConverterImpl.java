package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.Role;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.RoleDTO;
import org.springframework.stereotype.Component;

@Component
public class RoleConverterImpl implements GeneralConverter<Role, RoleDTO> {

    @Override
    public Role convertDTOToObject(RoleDTO object) {
        return null;
    }

    public RoleDTO convertObjectToDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getRoleName().name());
        return roleDTO;
    }
}
