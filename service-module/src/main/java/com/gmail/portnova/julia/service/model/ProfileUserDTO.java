package com.gmail.portnova.julia.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
public class ProfileUserDTO {
    private Long id;
    private UUID uuid;
    private String lastName;
    private String firstName;
    private String address;
    private String telephone;
}
