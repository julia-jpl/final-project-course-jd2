package com.gmail.portnova.julia.service;

import com.gmail.portnova.julia.service.model.ItemSaleDTO;
import com.gmail.portnova.julia.service.model.PageDTO;

import java.util.UUID;

public interface ItemSaleService {

    ItemSaleDTO findByUuid(String id);
}
