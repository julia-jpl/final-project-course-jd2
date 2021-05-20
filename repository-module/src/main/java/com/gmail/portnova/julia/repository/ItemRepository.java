package com.gmail.portnova.julia.repository;

import com.gmail.portnova.julia.repository.model.Item;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends GenericRepository<Long, Item> {
    List<Item> findAllWithLimit(int startPosition, int maxResult);

    Long countItemsByUserUuid(UUID userUuid);

    List<Item> findItemsWithLimitByUserUuid(int startPosition, int maxResult, UUID userUuid);

    List<Item> findAllWithoutRelationToSaleUser();
}
