package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.RoleRepository;
import com.gmail.portnova.julia.repository.model.Role;
import com.gmail.portnova.julia.service.RoleService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.RoleDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final GeneralConverter<Role, RoleDTO> roleConverter;

    public RoleServiceImpl(RoleRepository roleRepository, GeneralConverter<Role, RoleDTO> roleConverter) {
        this.roleRepository = roleRepository;
        this.roleConverter = roleConverter;
    }

    @Override
    @Transactional
    public List<RoleDTO> findAll() {
        List<Role> roles = roleRepository.findAll();
        if (!roles.isEmpty()) {
           return roles.stream().
                    map(roleConverter::convertObjectToDTO)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }


}
