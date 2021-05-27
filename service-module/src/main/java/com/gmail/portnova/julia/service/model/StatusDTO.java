package com.gmail.portnova.julia.service.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@EqualsAndHashCode
public class StatusDTO {
    private Long id;
    private String name;
}
