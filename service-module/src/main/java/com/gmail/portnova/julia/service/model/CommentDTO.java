package com.gmail.portnova.julia.service.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
public class CommentDTO {
    private Long id;
    private UUID uuid;
    private String content;
    private String userLastAndFirstName;
    private LocalDateTime createdAt;
}
