package com.gmail.portnova.julia.service.model;

import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class CommentDTO {
    private Long id;
    private UUID commentUuid;
    @NotNull(message = "Comment shouldn't be empty or blank")
    @NotBlank(message = "Comment shouldn't be empty or blank")
    @NotEmpty(message = "Comment shouldn't be empty or blank")
    @Size(max = 200, message = "the length of comment should be under 200 letters")
    private String content;
    private String userLastAndFirstName;
    private LocalDateTime createdAt;
    private String articleUuid;
    private UUID userUuid;
}
