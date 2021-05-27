package com.gmail.portnova.julia.service.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class FormOrderDTO {
    private String sellerUuid;
    private UUID customerUuid;
    private String itemUuid;
    private Integer itemQuantity;
    private String customerTel;
}
