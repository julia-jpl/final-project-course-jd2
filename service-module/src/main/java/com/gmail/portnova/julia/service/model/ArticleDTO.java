package com.gmail.portnova.julia.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
public class ArticleDTO {
    private Long id;
    private UUID uuid;
    @NotNull
    @NotBlank
    @Size(min = 2, max = 100)
    private String title;
    private String userLastAndFirstName;
    @Size(min = 10, max = 1000)
    private String content;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$",
            message = "Date doesn't match pattern")
    private String createdAt;
    private UUID userUuid;
    private String updatedAt;
}
