package com.gmail.portnova.julia.service.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class FeedbackDTO {
    private Long id;
    private String userFullName;
    private UUID uuid;
    private String text;
    private LocalDateTime createdAt;
    private Boolean displayed;
    private UUID userUuid;
}
