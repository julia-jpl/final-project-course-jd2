package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.Role;
import com.gmail.portnova.julia.repository.model.RoleNameEnum;
import com.gmail.portnova.julia.service.model.RoleDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RoleConverterImplTest {
    private final RoleConverterImpl roleConverter = new RoleConverterImpl();
    private Role role;

    @BeforeEach
    void init() {
        role = new Role();
    }

    @Test
    void shouldConvertRoleToRoleDTOAndReturnNotNullResult() {
        RoleDTO roleDTO = roleConverter.convertObjectToDTO(role);

        assertNotNull(roleDTO);
    }

    @Test
    void shouldConvertRoleToRoleDTOAndReturnRightId() {
        Long id = 1L;
        role.setId(id);
        RoleDTO roleDTO = roleConverter.convertObjectToDTO(role);

        assertEquals(id, roleDTO.getId());
    }

    @Test
    void shouldConvertRoleToRoleDTOAndReturnRightName() {
        RoleNameEnum roleNameEnum = RoleNameEnum.ADMINISTRATOR;
        role.setRoleName(roleNameEnum);
        RoleDTO roleDTO = roleConverter.convertObjectToDTO(role);

        assertEquals(roleNameEnum.name(), roleDTO.getName());
    }
}