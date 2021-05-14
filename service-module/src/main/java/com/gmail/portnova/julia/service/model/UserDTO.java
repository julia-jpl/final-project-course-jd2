package com.gmail.portnova.julia.service.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@EqualsAndHashCode
public class UserDTO {
    private Long id;
    private UUID uuid;
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;
    private String password;
    private String roleName;
}
