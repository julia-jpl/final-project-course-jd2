package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.ItemApiDTO;

public interface ItemApiService {
    ItemApiDTO getItemApiByUuid(String uuidString);

    ItemApiDTO deleteItemByUuid(String uuidString);

    ItemApiDTO addItemToDatabase(ItemApiDTO item);
}
