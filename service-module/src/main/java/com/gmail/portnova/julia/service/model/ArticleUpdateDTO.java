package com.gmail.portnova.julia.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
public class ArticleUpdateDTO {
    @Size(max = 100)
    @NotNull
    private String title;
    @Size(max = 1000)
    @NotNull
    private String content;
}
