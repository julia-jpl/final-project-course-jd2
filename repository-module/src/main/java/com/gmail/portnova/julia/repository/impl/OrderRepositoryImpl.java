package com.gmail.portnova.julia.repository.impl;

import com.gmail.portnova.julia.repository.OrderRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.repository.model.Order;
import com.gmail.portnova.julia.repository.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class OrderRepositoryImpl extends GenericRepositoryImpl<Long, Order> implements OrderRepository {
    @Override
    public Long countOrdersByCustomerUuid(UUID customerUuid) {
        String hql = "SELECT COUNT (o.id) FROM Order o JOIN o.orderDetail od JOIN od.customer odc WHERE odc.uuid = :id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", customerUuid);
        return (Long) query.getSingleResult();
    }

    @Override
    public List<Order> findOrdersWithLimitByCustomerUuid(int startPosition, int maxResult, UUID customerUuid) {
        String hql = "SELECT o FROM Order o JOIN o.orderDetail od JOIN od.customer odc WHERE odc.uuid = :id ORDER BY o.createdAt DESC";
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(startPosition);
        query.setMaxResults(maxResult);
        query.setParameter("id", customerUuid);
        return query.getResultList();
    }
}
