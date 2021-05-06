package com.gmail.portnova.julia.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "ROLE")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RoleNameEnum roleName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id.equals(role.id) && roleName.equals(role.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName);
    }
}
