package com.gmail.portnova.julia.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ARTICLE")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"content", "createdAt", "user"})
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(name = "unique_number")
    @Type(type = "uuid-char")
    private UUID uuid;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private String author;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
