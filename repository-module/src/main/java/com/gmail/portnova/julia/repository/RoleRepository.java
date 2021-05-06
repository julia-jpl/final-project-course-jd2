package com.gmail.portnova.julia.repository;

import com.gmail.portnova.julia.repository.model.Role;
import com.gmail.portnova.julia.repository.model.RoleNameEnum;

public interface RoleRepository extends GenericRepository<Long, Role> {
    Role findByName(RoleNameEnum name);
}
