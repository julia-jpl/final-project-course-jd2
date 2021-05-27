package com.gmail.portnova.julia.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_STATUS")
@Getter
@Setter
@EqualsAndHashCode
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "status_name")
    @Enumerated(EnumType.STRING)
    private OrderStatusNameEnum statusName;
}
