package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.repository.RoleRepository;
import com.gmail.portnova.julia.repository.model.Role;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.impl.RoleServiceImpl;
import com.gmail.portnova.julia.service.model.RoleDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private GeneralConverter<Role, RoleDTO> roleConverter;
    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void shouldFindAll() {
        List<Role> roles = Collections.emptyList();
        when(roleRepository.findAll()).thenReturn(roles);
        List<RoleDTO> roleDTOS = Collections.emptyList();
        assertEquals(roleDTOS, roleService.findAll());
    }

}