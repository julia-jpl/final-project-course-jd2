package com.gmail.portnova.julia.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ArticleApiDTO {
    private Long id;
    private UUID uuid;
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(max = 100)
    private String title;
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(max = 1000)
    private String content;
    private LocalDateTime createdAt;
    private UUID userUuid;
}
