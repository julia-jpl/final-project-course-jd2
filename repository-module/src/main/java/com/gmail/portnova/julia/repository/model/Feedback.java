package com.gmail.portnova.julia.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "FEEDBACK")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"user", "isDisplayed"})
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(name = "unique_number")
    @Type(type = "uuid-char")
    private UUID uuid;
    @Column
    private String text;
    @Column
    private String author;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "is_displayed")
    private Boolean isDisplayed;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
