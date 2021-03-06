package com.gmail.portnova.julia.service.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.SecondaryTable;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class ItemApiDTO {
    private String uniqueNumber;
    private UUID uuid;
    private String name;
    private BigDecimal price;
    private String description;
    private String itemGroup;
    private Boolean deleted;
    private List<String> userUuids = new ArrayList<>();
}
