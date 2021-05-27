package com.gmail.portnova.julia.repository.impl;

import com.gmail.portnova.julia.repository.ItemGroupRepository;
import com.gmail.portnova.julia.repository.model.ItemGroup;
import com.gmail.portnova.julia.repository.model.ItemGroupNameEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

@Repository
@Log4j2
public class ItemGroupRepositoryImpl extends GenericRepositoryImpl<Long, ItemGroup> implements ItemGroupRepository {
    @Override
    public ItemGroup findByName(ItemGroupNameEnum itemGroupNameEnum) {
        String hql = "FROM ItemGroup IG WHERE IG.itemGroupName = :name";
        Query query = entityManager.createQuery(hql);
        query.setParameter("name", itemGroupNameEnum);
        try {
            return (ItemGroup) query.getSingleResult();
        } catch (NonUniqueResultException | NoResultException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
