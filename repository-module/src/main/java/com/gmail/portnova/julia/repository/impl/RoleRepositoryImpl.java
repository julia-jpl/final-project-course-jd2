package com.gmail.portnova.julia.repository.impl;

import com.gmail.portnova.julia.repository.RoleRepository;
import com.gmail.portnova.julia.repository.model.Role;
import com.gmail.portnova.julia.repository.model.RoleNameEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import java.lang.invoke.MethodHandles;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl<Long, Role> implements RoleRepository {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Role findByName(RoleNameEnum name) {
        String hql = "FROM Role r WHERE r.roleName = :name";
        Query query = entityManager.createQuery(hql);
        query.setParameter("name", name);
        try {
            return (Role) query.getSingleResult();
        } catch (NonUniqueResultException | NoResultException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
