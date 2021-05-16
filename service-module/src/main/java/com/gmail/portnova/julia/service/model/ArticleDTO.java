package com.gmail.portnova.julia.service.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ArticleDTO {
    private Long id;
    private UUID uuid;
    private String title;
    private String userLastAndFirstName;
    private String content;
    private LocalDateTime createdAt;
}
