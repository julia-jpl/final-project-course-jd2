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
public class FeedbackDTO {
    private String userFullName;
    private UUID uuid;
    @NotEmpty
    @NotNull
    @NotBlank
    @Size(max = 500)
    private String text;
    private LocalDateTime createdAt;
    private Boolean displayed;
    private UUID userUuid;
}
