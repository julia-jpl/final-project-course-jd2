package com.gmail.portnova.julia.service.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@EqualsAndHashCode
public class SellerDTO {
    private UUID uuid;
    private String sellerName;
}
