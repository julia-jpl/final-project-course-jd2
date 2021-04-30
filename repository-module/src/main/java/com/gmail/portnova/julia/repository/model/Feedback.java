package com.gmail.portnova.julia.repository.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "FEEDBACK")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column
    private String text;
    @Column
    private LocalDate date;
    @Column
    private Boolean isDisplayed;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
