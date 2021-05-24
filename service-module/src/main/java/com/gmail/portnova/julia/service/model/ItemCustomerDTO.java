package com.gmail.portnova.julia.service.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@EqualsAndHashCode
public class ItemCustomerDTO {

    private UUID uuid;
    private String name;
    private BigDecimal price;
    private List<SellerDTO> sellers = new ArrayList<>();
}
