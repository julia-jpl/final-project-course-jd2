package com.gmail.portnova.julia.repository.impl;

import com.gmail.portnova.julia.repository.RoleRepository;
import com.gmail.portnova.julia.repository.model.Role;
import com.gmail.portnova.julia.repository.model.RoleNameEnum;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import java.lang.invoke.MethodHandles;

@Repository
@Log4j2
public class RoleRepositoryImpl extends GenericRepositoryImpl<Long, Role> implements RoleRepository {

    @Override
    public Role findByName(RoleNameEnum name) {
        String hql = "FROM Role r WHERE r.roleName = :name";
        Query query = entityManager.createQuery(hql);
        query.setParameter("name", name);
        try {
            return (Role) query.getSingleResult();
        } catch (NonUniqueResultException | NoResultException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
