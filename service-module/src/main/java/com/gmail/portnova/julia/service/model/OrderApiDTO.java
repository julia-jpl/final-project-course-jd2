package com.gmail.portnova.julia.service.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@EqualsAndHashCode
public class OrderApiDTO {
    private Long id;
    private String orderUuid;
    private String number;
    private String orderStatus;
    private String seller;
    private String itemName;
    private Integer itemQuantity;
    private BigDecimal totalPrice;
    private String date;
    private String customerIdentifier;
    private String customerTelephone;
}
