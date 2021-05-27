package com.gmail.portnova.julia.repository;

import com.gmail.portnova.julia.repository.model.ItemGroup;
import com.gmail.portnova.julia.repository.model.ItemGroupNameEnum;

public interface ItemGroupRepository extends GenericRepository<Long, ItemGroup> {
    ItemGroup findByName(ItemGroupNameEnum itemGroupNameEnum);
}
