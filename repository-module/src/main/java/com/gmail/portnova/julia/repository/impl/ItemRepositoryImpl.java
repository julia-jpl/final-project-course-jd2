package com.gmail.portnova.julia.repository.impl;

import com.gmail.portnova.julia.repository.ItemRepository;
import com.gmail.portnova.julia.repository.model.Item;
import com.gmail.portnova.julia.repository.model.ItemDetail;
import com.gmail.portnova.julia.repository.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Long, Item> implements ItemRepository {

    @Override
    public List<Item> findAllWithLimit(int startPosition, int maxResult) {
        String hql = "FROM Item i ORDER BY i.name";
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(startPosition);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public Long countItemsByUserUuid(UUID userUuid) {
        String hql = "SELECT COUNT (i.id) FROM Item i JOIN i.users U WHERE U.uuid = :id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", userUuid);
        return (Long) query.getSingleResult();
    }

    @Override
    public List<Item> findItemsWithLimitByUserUuid(int startPosition, int maxResult, UUID userUuid) {
        String hql = "SELECT i FROM Item i JOIN i.users U WHERE U.uuid = :id ORDER BY i.name";
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(startPosition);
        query.setMaxResults(maxResult);
        query.setParameter("id", userUuid);
        return query.getResultList();
    }

    @Override
    public List<Item> findAllWithoutRelationToSaleUser() {
        String hql = "SELECT i.id, i.uuid, i.uniqueNumber, i.name, i.itemDetail FROM Item i JOIN i.itemDetail";
        Query query = entityManager.createQuery(hql);
        List<Object> resultList = query.getResultList();
        List<Item> items = new ArrayList<>();
        for (Object o : resultList) {
            Item item = getItem((Object[]) o);
            items.add(item);
        }
        return items;
    }

    @Override
    public List<Item> findByUserUuid(UUID uuid) {
        String hql = "SELECT i FROM Item i JOIN i.users U WHERE U.uuid = :id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", uuid);
        return query.getResultList();
    }

    protected Item getItem(Object[] o) {
        Item item = new Item();
        Object[] objects = o;
        Long id = (Long) objects[0];
        item.setId(id);
        UUID uuid = (UUID) objects[1];
        item.setUuid(uuid);
        String uniqueNumber = (String) objects[2];
        item.setUniqueNumber(uniqueNumber);
        String name = (String) objects[3];
        item.setName(name);
        ItemDetail itemDetail = (ItemDetail) objects[4];
        item.setItemDetail(itemDetail);
        return item;
    }
}
