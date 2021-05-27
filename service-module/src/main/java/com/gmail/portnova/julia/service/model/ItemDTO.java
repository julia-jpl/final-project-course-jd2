package com.gmail.portnova.julia.service.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ItemDTO {
    private Long id;
    private String name;
    private String uniqueNumber;
    private UUID uuid;
    private BigDecimal price;

    @Override
    public String toString() {
        return "Item: " +
                "name='" + name + '\'' +
                ", uniqueNumber='" + uniqueNumber + '\'' +
                ", price=" + price +
                '.';
    }
}
