package com.gmail.portnova.julia.repository.impl;

import com.gmail.portnova.julia.repository.OrderStatusRepository;
import com.gmail.portnova.julia.repository.model.OrderStatus;
import com.gmail.portnova.julia.repository.model.OrderStatusNameEnum;
import com.gmail.portnova.julia.repository.model.Role;
import jdk.jfr.Registered;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
@Log4j2
@Repository
public class OrderStatusRepositoryImpl extends GenericRepositoryImpl<Long, OrderStatus> implements OrderStatusRepository {
    @Override
    public OrderStatus findByStatusName(OrderStatusNameEnum statusName) {
        String hql = "FROM OrderStatus OS WHERE OS.statusName = :name";
        Query query = entityManager.createQuery(hql);
        query.setParameter("name", statusName);
        try {
            return (OrderStatus) query.getSingleResult();
        } catch (NonUniqueResultException | NoResultException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
