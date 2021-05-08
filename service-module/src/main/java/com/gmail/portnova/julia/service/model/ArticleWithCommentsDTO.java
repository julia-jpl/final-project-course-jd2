package com.gmail.portnova.julia.service.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
public class ArticleWithCommentsDTO {
    private Long id;
    private UUID uuid;
    private String title;
    private String userLastAndFirstName;
    private String content;
    private LocalDateTime createdAt;
    private List<CommentDTO> comments;
}
