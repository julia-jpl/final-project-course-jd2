package com.gmail.portnova.julia.repository.impl;

import com.gmail.portnova.julia.repository.ItemDetailRepository;
import com.gmail.portnova.julia.repository.model.Item;
import com.gmail.portnova.julia.repository.model.ItemDetail;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public class ItemDetailRepositoryImpl extends GenericRepositoryImpl<Long, ItemDetail> implements ItemDetailRepository {
    @Override
    public BigDecimal findMinPrice() {
        String hql = "SELECT MIN (ID.price) FROM ItemDetail ID";
        Query query = entityManager.createQuery(hql);
        return (BigDecimal) query.getSingleResult();
    }

    @Override
    public List<Item> findItemDetailsWithLimitByUserUuid(int startPosition, int maxResult, UUID userUuid) {
        String hql = "SELECT ID.item FROM ItemDetail ID JOIN ID.item i JOIN ID.user U WHERE U.uuid = :id ORDER BY i.name";
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(startPosition);
        query.setMaxResults(maxResult);
        query.setParameter("id", userUuid);
        return query.getResultList();
    }

    @Override
    public Long countItemsByUserUuid(UUID userUuid) {
        String hql = "SELECT COUNT (i.id) FROM ItemDetail ID JOIN ID.item i JOIN ID.user U WHERE U.uuid = :id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", userUuid);
        return (Long) query.getSingleResult();
    }


}
