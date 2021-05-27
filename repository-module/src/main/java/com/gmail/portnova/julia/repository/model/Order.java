package com.gmail.portnova.julia.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"saleUser", "status", "orderDetail"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String number;
    @Column
    @Type(type = "uuid-char")
    private UUID uuid;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User saleUser;
    @Column
    private String seller;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private OrderStatus status;
    @OneToOne(fetch = FetchType.LAZY,
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private OrderDetail orderDetail;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
