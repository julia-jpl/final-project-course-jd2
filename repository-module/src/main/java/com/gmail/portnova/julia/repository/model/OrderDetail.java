package com.gmail.portnova.julia.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ORDER_DETAIL")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"customer"})
public class OrderDetail {
    @GenericGenerator(
    name = "generator",
    strategy = "foreign",
    parameters = @Parameter(name = "property", value = "order")
    )
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "order_id", nullable = false)
    private Long orderId;
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Column(name = "item_quantity")
    private Integer itemQuantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customer;
    @Column(name ="customer_telephone")
    private String customerTelephone;
    @Column(name = "customer_identifier")
    private String customerIdentifier;
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Order order;

}
