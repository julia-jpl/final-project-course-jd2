package com.gmail.portnova.julia.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ITEM")
@Getter
@Setter
@SQLDelete(sql = "UPDATE ITEM SET is_deleted = '1' WHERE id = ?")
@Where(clause = "is_deleted = '0'")
@EqualsAndHashCode(exclude = {"itemGroup", "description", "itemDetail"})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Type(type = "uuid-char")
    private UUID uuid;
    @Column(name = "unique_number")
    private String uniqueNumber;
    @Column
    private String name;
    @Column
    private String description;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private ItemGroup itemGroup;
    @OneToOne(fetch = FetchType.LAZY,
            mappedBy = "item",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private ItemDetail itemDetail;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ITEM_USER",
            joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> users = new ArrayList<>();
}
