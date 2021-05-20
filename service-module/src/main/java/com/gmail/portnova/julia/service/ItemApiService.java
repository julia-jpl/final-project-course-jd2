package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.ItemApiDTO;

public interface ItemApiService {
    ItemApiDTO getItemApiByUuid(String uuidString);

    void deleteItemApiByUuidNotRelatedToSaleUser(String uuidString);

    void addItemToDatabase(ItemApiDTO item);

}
