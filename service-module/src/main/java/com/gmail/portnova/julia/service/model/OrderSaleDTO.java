package com.gmail.portnova.julia.service.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderSaleDTO {
    private String number;
    private String orderUuid;
    private String orderStatus;
    private Long orderStatusId;
    private String itemName;
    private Integer itemQuantity;
    private BigDecimal totalPrice;
    private String date;
    private String customerIdentifier;
    private String customerTelephone;
}
