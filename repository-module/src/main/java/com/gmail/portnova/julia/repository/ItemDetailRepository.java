package com.gmail.portnova.julia.repository;

import com.gmail.portnova.julia.repository.model.Item;
import com.gmail.portnova.julia.repository.model.ItemDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ItemDetailRepository extends GenericRepository<Long, ItemDetail> {
    BigDecimal findMinPrice();
    List<Item> findItemDetailsWithLimitByUserUuid(int startPosition, int maxResult, UUID userUuid);

    Long countItemsByUserUuid(UUID userUuid);
}
