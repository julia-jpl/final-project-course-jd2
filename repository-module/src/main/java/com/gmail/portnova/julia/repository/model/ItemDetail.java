package com.gmail.portnova.julia.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "ITEM_DETAIL")
@EqualsAndHashCode(exclude = {"price", "item"})
public class ItemDetail {

    @GenericGenerator(
            name = "generator",
            strategy = "foreign",
            parameters = @Parameter(name = "property", value = "item")
    )
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "item_id", nullable = false)
    private Long itemId;
    @Column(scale = 2, precision = 10)
    private BigDecimal price;
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Item item;
}
