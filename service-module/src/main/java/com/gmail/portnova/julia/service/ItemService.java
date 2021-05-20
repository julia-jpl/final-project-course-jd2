package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.ItemDTO;
import com.gmail.portnova.julia.service.model.PageDTO;
import com.gmail.portnova.julia.service.model.UserDTO;

import java.util.List;
import java.util.UUID;

public interface ItemService {
    PageDTO<ItemDTO> getAllItemsPage(int pageNumber, int maxResult);

    PageDTO<ItemDTO> getSaleUserItemsPage(int pageNumber, int maxResult, UUID uuid);

    List<UserDTO> deleteItemFromSaleUserCatalog(String uuid, UUID currentUserUuid);

    String copyItemTOClipboard(String uuid);

    List<ItemDTO> getAllItems();
}
