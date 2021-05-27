package com.gmail.portnova.julia.service.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;
@Getter
@Setter
public class ItemSaleDTO {
    private String name;
    private String uniqueNumber;
    private UUID uuid;
    private BigDecimal price;
    private String description;
}
