package com.gmail.portnova.julia.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode
@Setter
@Table(name = "ITEM_GROUP")
public class ItemGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "group_name")
    @Enumerated(EnumType.STRING)
    private ItemGroupNameEnum itemGroupName;
}
