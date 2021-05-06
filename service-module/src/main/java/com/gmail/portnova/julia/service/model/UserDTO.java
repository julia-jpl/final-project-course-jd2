package com.gmail.portnova.julia.service.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
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
