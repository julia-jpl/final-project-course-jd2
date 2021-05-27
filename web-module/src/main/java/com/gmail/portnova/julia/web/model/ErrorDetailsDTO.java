package com.gmail.portnova.julia.web.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Data
public class ErrorDetailsDTO {
    private LocalDateTime localDateTime;
    private String message;
    private String details;
}
