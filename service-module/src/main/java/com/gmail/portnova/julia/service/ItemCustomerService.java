package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.ItemCustomerDTO;

public interface ItemCustomerService {
    ItemCustomerDTO findCustomerItemByUuid(String id);
}
